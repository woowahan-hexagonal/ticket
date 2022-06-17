package com.jummi.ticket.performance.converter;

import com.jummi.ticket.performance.adapter.persistence.PerformanceEntity;
import com.jummi.ticket.performance.adapter.persistence.SeriesEntity;
import com.jummi.ticket.performance.adapter.web.RegisterPerformanceRequest;
import com.jummi.ticket.performance.adapter.web.SeriesRequest;
import com.jummi.ticket.performance.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper
public interface PerformanceMapper {
    PerformanceEntity convertDomainEntityToJpaEntity(Performance performance);

    default Performance convertRequestToDomainEntity(RegisterPerformanceRequest request) {
        return Performance.builder()
                .title(request.getTitle())
                .genre(Genre.valueOf(request.getGenre()))
                .period(new Period(request.getStartDate(), request.getEndDate()))
                .venue(request.getVenue())
                .casts(request.getCasts())
                .runTime(request.getRunTime())
                .minAvailableAge(request.getMinAvailableAge())
                .reservationSites(convertStringToEnum(request.getReservationSites()))
                .build();
    }

    default List<ReservationSite> convertStringToEnum(List<String> reservationSites) {
        return reservationSites.stream()
                .map(ReservationSite::valueOf)
                .collect(Collectors.toList());
    }

    List<SeriesEntity> convertDomainEntityToJpaEntity(List<Series> series);

    default List<Series> extractSeriesDomainEntity(RegisterPerformanceRequest request) {
        return request.getSeries()
                .stream()
                .map(this::convertRequestToDomainEntity)
                .collect(Collectors.toList());
    }

    @Mapping(expression = "java(seriesRequest.getDateTime().toLocalDate())", target = "playDate")
    @Mapping(expression = "java(seriesRequest.getDateTime().toLocalTime())", target = "playTime")
    @Mapping(expression = "java(seriesRequest.isPerformed())", target = "isPerformed")
    Series convertRequestToDomainEntity(SeriesRequest seriesRequest);
}
