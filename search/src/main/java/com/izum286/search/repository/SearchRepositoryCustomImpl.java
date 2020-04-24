package com.izum286.search.repository;

import com.izum286.search.Models.FilterDTO;
import com.izum286.search.Models.FullCarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchRepositoryCustomImpl implements SearchRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public SearchRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<FullCarEntity> cityDatesPriceSortByPrice(String latitude, String longitude, double radius, LocalDateTime start, LocalDateTime end,
                                                         double priceFrom, double priceTo, Pageable pageable, boolean sort){
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if(sort){
            query.with(Sort.by(Sort.Direction.ASC, "pricePerDaySimple"));
        }

        criteria.add(Criteria.where("pricePerDay.value").gte(priceFrom));
        criteria.add(Criteria.where("pricePerDay.value").lte(priceTo));
        //TODO problem with pricePerDaySimple

        criteria.add(
                new Criteria()
                        .orOperator(Criteria.where("bookedPeriods").size(0),
                                Criteria.where("bookedPeriods").elemMatch(
                                        Criteria.where("startDateTime").gt(end).and("endDateTime").lt(start)
                                )
                        )
        );

        if(latitude!=null && longitude!=null){
            Point point = new Point(Double.parseDouble(latitude), Double.parseDouble(longitude));
            Distance distance = new Distance(radius, Metrics.KILOMETERS);
            Circle circle = new Circle(point, distance);
            Criteria geoCriteria = Criteria.where("pickUpPlace").withinSphere(circle);
            criteria.add(geoCriteria);
        }
        query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        List<FullCarEntity> list = mongoTemplate.find(query, FullCarEntity.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, FullCarEntity.class));
    }

    @Override
    public Page<FullCarEntity> byFilter(FilterDTO filter, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();
        if(filter.getMake()!=null){
            criteria.add(Criteria.where("make").is(filter.getMake()));
        }
        if(filter.getModel()!=null){
            criteria.add(Criteria.where("model").is(filter.getModel()));
        }
        if(filter.getYear()!=null){
            criteria.add(Criteria.where("year").is(filter.getYear()));
        }
        if(filter.getEngine()!=null){
            criteria.add(Criteria.where("engine").is(filter.getEngine()));
        }
        if(filter.getFuel()!=null){
            criteria.add(Criteria.where("fuel").is(filter.getFuel()));
        }
        if(filter.getGear()!=null){
            criteria.add(Criteria.where("gear").is(filter.getGear()));
        }
        if(filter.getWheels_drive()!=null){
            criteria.add(Criteria.where("wheelsDrive").is(filter.getWheels_drive()));
        }
        if(filter.getHorsepower()!=null){
            criteria.add(Criteria.where("horsePower").is(filter.getHorsepower()));
        }
        if(filter.getFuel_consumption()!=null){
            criteria.add(Criteria.where("fuelConsumption").is(filter.getFuel_consumption()));
        }
        query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        try {
            List<FullCarEntity> list = mongoTemplate.find(query, FullCarEntity.class);
            return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, FullCarEntity.class));
        } catch (Exception e) {
            throw new RuntimeException("something went wrong in repository");
        }
    }

    @Override
    public Page<FullCarEntity> searchAllSortByPrice(int itemsOnPage, int currentPage, FilterDTO filter,
                                                    String latt, String longt, String radius,
                                                    LocalDateTime dateFrom, LocalDateTime dateTo,
                                                    double minPrice, double maxPrice, Pageable pageable, boolean sort) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();
        if(sort){
            query.with(Sort.by(Sort.Direction.ASC, "pricePerDaySimple"));
        }

        if(latt!=null && longt!=null && radius!=null){
            Point point = new Point(Double.parseDouble(latt), Double.parseDouble(longt));
            Distance distance = new Distance(Double.parseDouble(radius), Metrics.KILOMETERS);
            Circle circle = new Circle(point, distance);
            Criteria geoCriteria = Criteria.where("pickUpPlace").withinSphere(circle);
            criteria.add(geoCriteria);
        }

        criteria.add(Criteria.where("pricePerDay.value").gte(minPrice));
        criteria.add(Criteria.where("pricePerDay.value").lte(maxPrice));
        criteria.add(
                new Criteria()
                        .orOperator(Criteria.where("bookedPeriods").size(0),
                                Criteria.where("bookedPeriods").elemMatch(
                                        Criteria.where("startDateTime").gt(dateTo).and("endDateTime").lt(dateFrom)
                                )
                        )
        );

        if(filter.getMake()!=null){
            criteria.add(Criteria.where("make").is(filter.getMake()));
        }
        if(filter.getModel()!=null){
            criteria.add(Criteria.where("model").is(filter.getModel()));
        }
        if(filter.getYear()!=null){
            criteria.add(Criteria.where("year").is(filter.getYear()));
        }
        if(filter.getEngine()!=null){
            criteria.add(Criteria.where("engine").is(filter.getEngine()));
        }
        if(filter.getFuel()!=null){
            criteria.add(Criteria.where("fuel").is(filter.getFuel()));
        }
        if(filter.getGear()!=null){
            criteria.add(Criteria.where("gear").is(filter.getGear()));
        }
        if(filter.getWheels_drive()!=null){
            criteria.add(Criteria.where("wheelsDrive").is(filter.getWheels_drive()));
        }
        if(filter.getHorsepower()!=null){
            criteria.add(Criteria.where("horsePower").is(filter.getHorsepower()));
        }
        if(filter.getFuel_consumption()!=null){
            criteria.add(Criteria.where("fuelConsumption").is(filter.getFuel_consumption()));
        }

        query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        try {
            List<FullCarEntity> list = mongoTemplate.find(query, FullCarEntity.class);
            return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, FullCarEntity.class));
        } catch (Exception e) {
            throw new RuntimeException("something went wrong in repository");
        }
    }
}
