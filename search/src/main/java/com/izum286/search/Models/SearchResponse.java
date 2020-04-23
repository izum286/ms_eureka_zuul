package com.izum286.search.Models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SearchResponse {
    private int currentPage;
    private int itemsOnPage;
    private long itemsTotal;
    List<FullCarDTOResponse> cars;
    CarStatDto statisticDtoForCurrentCar;
    String megaFilter;
}
