package com.izum286.search.Controller;

import com.izum286.search.Models.FilterDTO;
import com.izum286.search.Models.SearchResponse;
import com.izum286.search.Service.SearchService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;


    @GetMapping()
    public SearchResponse cityDatesPriceSortByPrice(@RequestParam(name = "latitude") String latitude,
                                                    @RequestParam(name = "longitude") String longitude,
                                                    @RequestParam (name = "start_date")
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                                            LocalDateTime dateFrom,
                                                    @RequestParam (name = "end_date")
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                                            LocalDateTime dateTo,
                                                    @RequestParam (name = "min_amount") double minPrice,
                                                    @RequestParam (name = "max_amount") double maxPrice,
                                                    @RequestParam (name = "ascending") boolean sort,
                                                    @RequestParam (name = "items_on_page") int itemsOnPage,
                                                    @RequestParam (name = "current_page")int currentPage) throws ServiceUnavailableException {
        double radius = 0.5;
        return searchService
                .cityDatesPriceSortByPrice
                        (latitude,  longitude,  radius, dateFrom, dateTo, minPrice, maxPrice, sort, itemsOnPage, currentPage);

    }

    /**
     *
     * @author izum286
     * status READY
     */
    @SneakyThrows
    @GetMapping("/geo")
    public SearchResponse geoAndRadius(@RequestParam (name = "latitude") String latt,
                                       @RequestParam (name = "longitude") String longt,
                                       @RequestParam (name = "radius") String radius,
                                       @RequestParam (name = "items_on_page") int itemsOnPage,
                                       @RequestParam (name = "current_page") int currentPage){
        return searchService.
                geoAndRadius
                        (latt, longt, radius, itemsOnPage, currentPage);
    }


    /**
     *
     * @author izum286
     * status READY
     */

    @GetMapping("/filters")
    public SearchResponse byFilter(@RequestBody FilterDTO filter,
                                   @RequestParam (name = "items_on_page") int itemsOnPage,
                                   @RequestParam (name = "current_page") int currentPage){
        return searchService
                .byFilter(filter, itemsOnPage, currentPage);
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        return searchService.getFilterForResponses();
    }


    /**
     *
     * @author izum286
     */
    @GetMapping("/all")
    public SearchResponse searchAllSortByPrice(@RequestParam (name = "items_on_page")int itemsOnPage,
                                               @RequestParam (name = "current_page")int currentPage,
                                               @RequestBody FilterDTO filter,
                                               @RequestParam (name = "latitude") String latt,
                                               @RequestParam (name = "longitude")String longt,
                                               @RequestParam (name = "radius")String radius,
                                               @RequestParam (name = "start_date")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                                       LocalDateTime dateFrom,
                                               @RequestParam (name = "end_date")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                                       LocalDateTime dateTo,
                                               @RequestParam (name = "min_amount")double minPrice,
                                               @RequestParam (name = "max_amount")double maxPrice,
                                               @RequestParam (name = "ascending") boolean sort

    ){
        return searchService.searchAllSortByPrice(itemsOnPage, currentPage, filter, latt,
                longt, radius,  dateFrom, dateTo, minPrice, maxPrice, sort);

    }
}
