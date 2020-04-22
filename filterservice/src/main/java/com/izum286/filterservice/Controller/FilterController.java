package com.izum286.filterservice.Controller;


import com.izum286.filterservice.Models.AddUpdateCarDtoRequest;
import com.izum286.filterservice.Service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController

public class FilterController {

    @Autowired
    FilterService filterService;

    @KafkaListener(topics = "add_filter")
    @GetMapping()
    public void addFilter (AddUpdateCarDtoRequest addUpdateCarDtoRequest){
        filterService.addFilter(addUpdateCarDtoRequest);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilters(){
        return filterService.provideFilter();
    }

    /**
     * deleting all filters from db
     * use CAREFULLY!!!!!!
     * @return String
     */
    @DeleteMapping()
    public String deleteAll() {
        filterService.deleteFilters();
        return filterService.provideFilter();
    }

}
