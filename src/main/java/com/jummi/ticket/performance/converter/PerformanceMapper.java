package com.jummi.ticket.performance.converter;

import com.jummi.ticket.performance.adapter.persistence.PerformanceEntity;
import com.jummi.ticket.performance.adapter.persistence.SeriesEntity;
import com.jummi.ticket.performance.adapter.web.RegisterPerformanceRequest;
import com.jummi.ticket.performance.adapter.web.SeriesRequest;
import com.jummi.ticket.performance.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PerformanceMapper {

    @Mapping(expression = "java(performance.getPerformanceId())", target = "performanceId")
    @Mapping(expression = "java(performance.getPeriod().startDate())", target = "startDate")
    @Mapping(expression = "java(performance.getPeriod().endDate())", target = "endDate")
    PerformanceEntity convertDomainEntityToJpaEntity(Performance performance);

    default Performance convertRequestToDomainEntity(RegisterPerformanceRequest request) {
        return Performance.builder()
                .title(request.getTitle())
                .genre(Genre.valueOf(request.getGenre()))
                .period(new Period(request.getStartDate(), request.getEndDate()))
                .venue(request.getVenue())
                .runTime(request.getRunTime())
                .minAvailableAge(request.getMinAvailableAge())
                .reservationSites(convertStringToEnum(request.getReservationSites()))
                .build();
    }

    private List<ReservationSite> convertStringToEnum(List<String> reservationSites) {
        return reservationSites.stream()
                .map(ReservationSite::valueOf)
                .collect(Collectors.toList());
    }

    default List<Series> extractSeriesDomainEntity(RegisterPerformanceRequest request) {
        return request.getSeries()
                .stream()
                .map(this::convertRequestToDomainEntity)
                .collect(Collectors.toList());
    }

    private Series convertRequestToDomainEntity(SeriesRequest seriesRequest) {
        return Series.builder()
                .playDate(seriesRequest.getDateTime().toLocalDate())
                .playTime(seriesRequest.getDateTime().toLocalTime())
                .casts(seriesRequest.getCasts())
                .isPerformed(seriesRequest.isPerformed())
                .build();
    }

    @Mapping(source = "performanceEntity", target = "performance")
    default List<SeriesEntity> convertDomainEntitiesToJpaEntities(List<Series> series, PerformanceEntity performanceEntity) {
        return series.stream()
                .map(s -> convertDomainEntityToJpaEntity(s, performanceEntity))
                .collect(Collectors.toList());
    }

    @Mapping(source = "performanceEntity", target = "performance")
    SeriesEntity convertDomainEntityToJpaEntity(Series series, PerformanceEntity performanceEntity);
}
