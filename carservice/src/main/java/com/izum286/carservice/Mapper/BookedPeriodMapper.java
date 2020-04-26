package com.izum286.carservice.Mapper;

import com.izum286.carservice.model.dto.BookRequestDTO;
import com.izum286.carservice.model.dto.BookResponseDTO;
import com.izum286.carservice.model.dto.BookedPeriodDto;
import com.izum286.carservice.model.entity.BookedPeriodEntity;
import com.izum286.search.Models.BookedCarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author  AleksGor
 * @author  Anton Konkin (refactoring)
 * izum286
 */
@Mapper(uses = CommentMapper.class, imports = {BookedPeriodEntity.class}
        ,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface BookedPeriodMapper {

    BookedPeriodMapper INSTANCE = Mappers.getMapper(BookedPeriodMapper.class);

    BookedPeriodEntity map(BookedPeriodDto dto);

    @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "startDateTime", source = "start_date_time")
    @Mapping(target = "endDateTime", source = "end_date_time")
    @Mapping(target = "paid", expression = "java(false)")
    @Mapping(target = "active", expression = "java(true)")
    @Mapping(target = "bookingDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "personWhoBooked", source = "person_who_booked")
    BookedPeriodEntity map(BookRequestDTO dto);

    @Mapping(target = "order_number", source = "orderId")
    @Mapping(target = "booking_date", source = "bookingDate")
    BookResponseDTO mapToResponse(BookedPeriodEntity entity);


    /**
     * BookedPeriodEntity -> BookedPeriodDto
     * @param entity BookedPeriodEntity
     * @return BookedPeriodDto
     */
    @Named("BookedPeriodFullMapper")
    @Mapping(target = "order_id", source = "orderId")
    @Mapping(target = "start_date_time", source = "startDateTime")
    @Mapping(target = "end_date_time", source = "endDateTime")
    @Mapping(target = "booking_date", source = "bookingDate")
    @Mapping(target = "person_who_booked", source = "personWhoBooked")
    BookedPeriodDto map(BookedPeriodEntity entity);

    /**
     * used for Get car by id for users - providing only start & and dates
     */
    @Named("mapForGetCarByIdForUsers")
    @Mapping(target = "paid", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "person_who_booked", ignore = true)
    @Mapping(target = "order_id", ignore = true)
    @Mapping(target = "booking_date", ignore = true)
    @Mapping(target = "start_date_time", source = "startDateTime")
    @Mapping(target = "end_date_time", source = "endDateTime")
    BookedPeriodDto mapForGetCarByIdForUsers(BookedPeriodEntity entity);


    //PersonWhoBooked map(PersonWhoBookedDto dto);

    //PersonWhoBookedDto map(PersonWhoBooked entity);

    @Named("mapBookedPeriodToBookedCar")
    @Mapping(target = "booked_period.order_id", source = "orderId")
    @Mapping(target = "booked_period.start_date_time", source = "startDateTime")
    @Mapping(target = "booked_period.end_date_time", source = "endDateTime")
    @Mapping(target = "booked_period.booking_date", source = "bookingDate")
    @Mapping(target = "booked_period.person_who_booked", source = "personWhoBooked")
    @Mapping(target = "serial_number", source = "carId")
    BookedCarDto mapBookedPeriodToBookedCarDto(BookedPeriodEntity bookedPeriodEntity);
}
