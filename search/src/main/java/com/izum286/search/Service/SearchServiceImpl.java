package com.izum286.search.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izum286.search.Client.FilterServiceClient;
import com.izum286.search.Exceptions.NotFoundServiceException;
import com.izum286.search.Exceptions.ServiceException;
import com.izum286.search.Mapper.CarMapper;
import com.izum286.search.Models.*;

import com.izum286.search.Models.SearchResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("Convert2MethodRef")
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    DiscoveryClient discoveryClient;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    FilterServiceClient filterServiceClient;

    public URI getEndpointForOtherService(String serviceId, String endpointName) throws ServiceUnavailableException {
        Optional<URI> serviceUri = discoveryClient.getInstances(serviceId)
                .stream()
                .map(service->service.getUri())
                .findFirst();
        URI endpoint = serviceUri.map(s -> s.resolve(endpointName)).orElseThrow(ServiceUnavailableException::new);
        return endpoint;
    }

    public String getFilterForResponses() throws ServiceUnavailableException {
//        RestTemplate restTemplate = new RestTemplate();
//        URI filterServiceEndpoint = getEndpointForOtherService("filter", "/get");
//        return restTemplate.getForEntity(filterServiceEndpoint, String.class).getBody();
        return filterServiceClient.getFilters();

    }

    @Override
    public SearchResponse cityDatesPriceSortByPrice(String latitude, String longitude, double radius,
                                                    LocalDateTime dateFrom, LocalDateTime dateTo,
                                                    double minPrice, double maxPrice, boolean sort, int itemsOnPage, int currentPage) throws ServiceUnavailableException {
        currentPage = (currentPage <= 0) ? 1: currentPage;
        Double rad = radius;
        double maxRad = rad+1500;
        Page<FullCarEntity> cars = new PageImpl<>(new ArrayList<>(), Pageable.unpaged(),0);
        //todo need to add this endpoint in carservice
        //при реализации карсервиса надо сделать новый эндпойнт - тк его раньше не были и лазили напрямую в кар репо
        //теперь будем обращаться к кар контроллеру и в нем придется прописать кучу новых конечных точек
        URI carserviceEndPoint = getEndpointForOtherService("car", "/cityDatesPriceSortByPrice");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(carserviceEndPoint)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("radius", radius)
                .queryParam("start_date", dateFrom)
                .queryParam("end_date", dateTo)
                .queryParam("min_amount", minPrice)
                .queryParam("max_amount", maxPrice)
                .queryParam("ascending", sort)
                .queryParam("items_on_page", itemsOnPage)
                .queryParam("current_page", currentPage);

        RestTemplate restTemplate = new RestTemplate();
        while ((cars.getTotalElements() == 0) & (rad<=maxRad)) {
            try {
                cars = restTemplate.getForObject(builder.toUriString(), Page.class);
//                cars = carRepository
//                        .cityDatesPriceSortByPrice(latitude, longitude, rad, dateFrom, dateTo, minPrice, maxPrice,
//                                PageRequest.of(currentPage, itemsOnPage), sort);
            } catch (Throwable t) {
                throw new ServiceUnavailableException("Something went wrong");
            }
            rad+=500;
        }
        if (cars.getTotalElements() == 0) throw new RuntimeException("No such Cars according to search request");
        SearchResponse res = new SearchResponse();
        List<FullCarDTOResponse> carDTOResponses = cars.getContent().stream().map(e -> CarMapper.INSTANCE.map(e)).collect(Collectors.toList());
        res.setCars(carDTOResponses);
        res.setCurrentPage(cars.getPageable().getPageNumber());
        res.setItemsOnPage(cars.getPageable().getPageSize());
        res.setItemsTotal(cars.getTotalElements());
        res.setMegaFilter(getFilterForResponses());
        return res;
    }

    @Override
    public SearchResponse geoAndRadius(String latt, String longt, String radius, int itemsOnPage, int currentPage) throws ServiceUnavailableException {
        currentPage = (currentPage <= 0) ? 1: currentPage;
        Double rad = Double.valueOf(radius);
        double maxRad = rad+1500;
        Page<FullCarEntity> cars = new PageImpl<>(new ArrayList<>(), Pageable.unpaged(),0);
        URI carserviceEndPoint = getEndpointForOtherService("car", "/findAllByPickUpPlaceWithin");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(carserviceEndPoint)
                .queryParam("latitude", latt)
                .queryParam("longitude", longt)
                .queryParam("radius", radius)
                .queryParam("items_on_page", itemsOnPage)
                .queryParam("current_page", currentPage);
        RestTemplate restTemplate =new RestTemplate();
        while ((cars.getTotalElements() == 0) & (rad<=maxRad)) {
            try {
                  cars = restTemplate.getForObject(builder.toUriString(), Page.class);
//                cars = carRepository
//                        .findAllByPickUpPlaceWithin(
//                                new Circle(Double.parseDouble(latitude), Double.parseDouble(longitude), rad),
//                                PageRequest.of(currentPage, itemsOnPage));
            } catch (Throwable e) {
                throw new ServiceUnavailableException("Something went wrong");
            }
            rad+=500;
        }
        if (cars.getTotalElements() == 0) throw new NotFoundServiceException("No such Cars according to search request");
        SearchResponse res = new SearchResponse();
        List<FullCarDTOResponse> carDTOResponses = cars
                .getContent().stream()
                .map(e -> CarMapper.INSTANCE.map(e))
                .collect(Collectors.toList());
        res.setCars(carDTOResponses);
        res.setCurrentPage(cars.getPageable().getPageNumber());
        res.setItemsOnPage(cars.getPageable().getPageSize());
        res.setItemsTotal(cars.getTotalElements());
        res.setMegaFilter(getFilterForResponses());
        return res;
    }

    @SneakyThrows
    @Override
    public SearchResponse byFilter(FilterDTO filter, int itemsOnPage, int currentPage) {
        currentPage = (currentPage <= 0) ? 1: currentPage;
        RestTemplate restTemplate = new RestTemplate();
        Page<FullCarEntity> cars = new PageImpl<>(new ArrayList<>(), Pageable.unpaged(),0);
        URI carserviceEndPoint = getEndpointForOtherService("car", "/byFilter");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(carserviceEndPoint)
                .queryParams(new ObjectMapper().convertValue(filter, LinkedMultiValueMap.class))
                .queryParam("items_on_page", itemsOnPage)
                .queryParam("current_page", currentPage);
        try {
            cars = restTemplate.getForObject(builder.toUriString(), Page.class);
//            cars  = carRepository
//                    .byFilter(filter, PageRequest.of(currentPage,itemsOnPage));

        } catch (Throwable t) {
            throw new NotFoundServiceException("Something went wrong");
        }
        if (cars.getTotalElements() == 0) throw new NotFoundServiceException("No such Cars according to search request");
        SearchResponse res = new SearchResponse();
        List<FullCarDTOResponse> carDTOResponses = cars.stream().map(e -> CarMapper.INSTANCE.map(e)).collect(Collectors.toList());
        res.setCars(carDTOResponses);
        res.setCurrentPage(cars.getPageable().getPageNumber());
        res.setItemsOnPage(cars.getPageable().getPageSize());
        res.setItemsTotal(cars.getTotalElements());
        res.setMegaFilter(getFilterForResponses());
        return res;

    }

    @SneakyThrows
    @Override
    public SearchResponse searchAllSortByPrice(int itemsOnPage, int currentPage,
                                               FilterDTO filter, String latt, String longt, String radius,
                                               LocalDateTime dateFrom, LocalDateTime dateTo,
                                               double minPrice, double maxPrice, boolean sort) {
        currentPage = (currentPage <= 0) ? 1: currentPage;
        Double rad = Double.valueOf(radius);
        double maxRad = rad+1500;
        RestTemplate restTemplate = new RestTemplate();
        Page<FullCarEntity> cars = new PageImpl<>(new ArrayList<>(), Pageable.unpaged(),0);
        URI carserviceEndPoint = getEndpointForOtherService("car", "/searchAllSortByPrice");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(carserviceEndPoint)
                .queryParam("items_on_page", itemsOnPage)
                .queryParam("current_page", currentPage)
                .queryParams(new ObjectMapper().convertValue(filter, LinkedMultiValueMap.class))
                .queryParam("latitude", latt)
                .queryParam("longitude", longt)
                .queryParam("radius", radius)
                .queryParam("start_date", dateFrom)
                .queryParam("end_date", dateTo)
                .queryParam("min_amount", minPrice)
                .queryParam("max_amount", maxPrice)
                .queryParam("ascending", sort)
                ;
        while ((cars.getTotalElements()==0) & (rad<=maxRad)) {
            try {
                cars = restTemplate.getForObject(builder.toUriString(), Page.class);
//                cars = carRepository
//                        .searchAllSortByPrice(itemsOnPage, currentPage, filter, latt, longt, rad.toString(),  dateFrom, dateTo, minPrice, maxPrice,
//                                PageRequest.of(currentPage, itemsOnPage), sort);
            } catch (Throwable e) {
                throw new ServiceException("Something went wrong");
            }
            rad+=500;
        }
        if (cars.getTotalElements() == 0)
            throw new NotFoundServiceException("No such Cars according to search request");
        SearchResponse res = new SearchResponse();
        List<FullCarDTOResponse> carDTOResponses = cars
                .getContent().stream()
                .map(e -> CarMapper.INSTANCE.map(e)).collect(Collectors.toList());
        res.setCars(carDTOResponses);
        res.setCurrentPage(cars.getPageable().getPageNumber());
        res.setItemsOnPage(cars.getPageable().getPageSize());
        res.setItemsTotal(cars.getTotalElements());
        res.setMegaFilter(getFilterForResponses());
        return res;
    }

}
