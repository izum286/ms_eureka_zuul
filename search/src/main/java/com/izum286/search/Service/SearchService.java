package com.izum286.search.Service;

import com.izum286.search.Models.*;
import com.izum286.search.Models.SearchResponse;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;

public interface SearchService {

    public String getFilterForResponses(); //for test
    SearchResponse cityDatesPriceSortByPrice(String latitude, String longitude, double radius,
                                             LocalDateTime dateFrom,
                                             LocalDateTime dateTo,
                                             double minPrice,
                                             double maxPrice,
                                             boolean sort,
                                             int itemsOnPage,
                                             int currentPage) throws ServiceUnavailableException;

    SearchResponse geoAndRadius(String latt,
                                String longt,
                                String radius,
                                int itemsOnPage,
                                int currentPage) throws ServiceUnavailableException;

    SearchResponse byFilter(FilterDTO filter,
                            int itemsOnPage,
                            int currentPage);

    SearchResponse searchAllSortByPrice(int itemsOnPage, int currentPage, FilterDTO filter, String latt, String longt, String radius,
                                        LocalDateTime dateFrom,
                                        LocalDateTime dateTo, double minPrice, double maxPrice, boolean sort
    );
}
