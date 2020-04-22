package com.izum286.filterservice.Service;


import com.izum286.filterservice.Models.AddUpdateCarDtoRequest;
import com.izum286.filterservice.Models.FilterDTO;
import com.izum286.filterservice.Models.FilterNodeEntity;

public interface FilterService {

    /**
     * Method for automatically add new filter from /upload page
     * call -> addNode
     * @param addUpdateCarDtoRequest
     * @author izum286
     */
    void addFilter(AddUpdateCarDtoRequest addUpdateCarDtoRequest);

    /**
     * Method return json string of all filters
     * @return
     * @author izum286
     */
    String provideFilter() ;

    /**
     * Method added new node from FullCarDto from data which typed
     * in /upload page
     * addFilter->(called by)->addNode->(call) ->dtoToNode()+mergeNodes()
     * @param filterDTO
     * @author izum286
     */
    void addNode(FilterDTO filterDTO) ;

    /**
     * Recursive Method that takes 2 nodes to merge him in point of different values
     * node toMerge are shifted relatively existed node by 1 node down.
     * @param exist
     * @param toMerge
     * @author izum286
     */
    void mergeNodes(FilterNodeEntity exist, FilterNodeEntity toMerge);

    /**
     * Method to find index of correspondent node in list of childs
     * @param where
     * @param from
     * @return
     * @author izum286
     */
    int findNextIndx(FilterNodeEntity where, FilterNodeEntity from);


    void deleteFilters();
}
