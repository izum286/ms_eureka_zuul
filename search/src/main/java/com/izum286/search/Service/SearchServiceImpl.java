package com.izum286.search.Service;

import com.izum286.search.Client.FilterServiceClient;
import com.izum286.search.Exceptions.NotFoundServiceException;
import com.izum286.search.Mapper.CarMapper;
import com.izum286.search.Models.FilterDTO;
import com.izum286.search.Models.FullCarDTOResponse;
import com.izum286.search.Models.FullCarEntity;
import com.izum286.search.Models.SearchResponse;
import com.izum286.search.repository.SearchRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("Convert2MethodRef")
@Service
public class SearchServiceImpl implements SearchService {


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    FilterServiceClient filterServiceClient;

    @Autowired
    SearchRepository searchRepository;



//    public URI getEndpointForOtherService(String serviceId, String endpointName) throws ServiceUnavailableException {
//        Optional<URI> serviceUri = discoveryClient.getInstances(serviceId)
//                .stream()
//                .map(service->service.getUri())
//                .findFirst();
//        URI endpoint = serviceUri.map(s -> s.resolve(endpointName)).orElseThrow(ServiceUnavailableException::new);
//        return endpoint;
//    }
    @Override
    public String getFilterForResponses()  {
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

        while ((cars.getTotalElements() == 0) & (rad<=maxRad)) {
            try {
                cars = searchRepository
                        .cityDatesPriceSortByPrice(latitude, longitude, rad, dateFrom, dateTo, minPrice, maxPrice,
                                PageRequest.of(currentPage, itemsOnPage), sort);
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

        while ((cars.getTotalElements() == 0) & (rad<=maxRad)) {
            try {

                cars = searchRepository
                        .findAllByPickUpPlaceWithin(
                                new Circle(Double.parseDouble(latt), Double.parseDouble(longt), rad),
                                PageRequest.of(currentPage, itemsOnPage));
            } catch (Throwable e) {
                throw new ServiceUnavailableException("Something went wrong");
            }
            rad+=500;
        }
        if (cars.getTotalElements() == 0) throw new RuntimeException("No such Cars according to search request");
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
        Page<FullCarEntity> cars = new PageImpl<>(new ArrayList<>(), Pageable.unpaged(),0);
        try {

            cars  = searchRepository
                    .byFilter(filter, PageRequest.of(currentPage,itemsOnPage));

        } catch (Throwable t) {
            throw new RuntimeException("Something went wrong");
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
        Page<FullCarEntity> cars = new PageImpl<>(new ArrayList<>(), Pageable.unpaged(),0);
        while ((cars.getTotalElements()==0) & (rad<=maxRad)) {
            try {
                cars = searchRepository
                        .searchAllSortByPrice(itemsOnPage, currentPage, filter, latt, longt, rad.toString(),  dateFrom, dateTo, minPrice, maxPrice,
                                PageRequest.of(currentPage, itemsOnPage), sort);
            } catch (Throwable e) {
                throw new RuntimeException("Something went wrong");
            }
            rad+=500;
        }
        if (cars.getTotalElements() == 0)
            throw new RuntimeException("No such Cars according to search request");
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
