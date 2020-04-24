package com.izum286.search.repository;

import com.izum286.search.Models.FilterDTO;
import com.izum286.search.Models.FullCarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface SearchRepositoryCustom {
    Page<FullCarEntity> cityDatesPriceSortByPrice (String latitude, String longitude, double radius, LocalDateTime start, LocalDateTime end,
                                                   double priceFrom, double priceTo, Pageable pageable, boolean sort);

    Page<FullCarEntity> byFilter(FilterDTO filter, Pageable pageable);
    Page<FullCarEntity> searchAllSortByPrice(int itemsOnPage, int currentPage, FilterDTO filter,
                                             String latt, String longt, String radius,
                                             LocalDateTime dateFrom, LocalDateTime dateTo,
                                             double minPrice, double maxPrice,  Pageable pageable, boolean sort);
}
