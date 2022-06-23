package com.jummi.ticket.performance.converter;

import com.jummi.ticket.performance.adapter.persistence.PerformanceEntity;
import com.jummi.ticket.performance.adapter.persistence.SeriesEntity;
import com.jummi.ticket.performance.adapter.web.RegisterPerformanceRequest;
import com.jummi.ticket.performance.adapter.web.SeriesRequest;
import com.jummi.ticket.performance.domain.*;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PerformanceMapperTest {

    private PerformanceMapper mapper = Mappers.getMapper(PerformanceMapper.class);

    private List<LocalDate> playDates;
    private List<LocalTime> playTimes;
    private List<List<String>> casts;

    @BeforeEach
    void setUp() {
        playDates = List.of(
                LocalDate.of(2022, 5, 13),
                LocalDate.of(2022, 5, 14),
                LocalDate.of(2022, 5, 15)
        );
        playTimes = List.of(LocalTime.of(15, 0), LocalTime.of(12, 0));
        casts = List.of(List.of("잔나비", "솔루션스"), List.of("LUCY", "daybreak", "폴킴"), List.of("소란", "멜로망스", "N.Flying"));
    }

    @Test
    @DisplayName("convert performance domain entity to performance jpa entity")
    void convertPerformanceDomainEntityToJpaEntity() {
        Performance performance = createPerformance();
        PerformanceEntity performanceEntity = mapper.convertDomainEntityToJpaEntity(performance);

        assertAll(
                () -> assertThat(performanceEntity.getTitle()).isEqualTo("BML"),
                () -> assertThat(performanceEntity.getGenre()).isEqualTo(Genre.CONCERT),
                () -> assertThat(performanceEntity.getStartDate()).isEqualTo("2022-05-13"),
                () -> assertThat(performanceEntity.getEndDate()).isEqualTo("2022-05-15"),
                () -> assertThat(performanceEntity.getRunTime()).isEqualTo(540),
                () -> assertThat(performanceEntity.getReservationSites()).hasSize(2)
        );
    }

    @Test
    @DisplayName("convert performance request to performance domain entity")
    void convertPerformanceRequestToDomainEntity() {
        RegisterPerformanceRequest request = createRegisterPerformanceRequest();
        Performance performance = mapper.convertRequestToDomainEntity(request);

        assertAll(
                () -> assertThat(performance.getPerformanceId()).isNull(),
                () -> assertThat(performance.getTitle()).isEqualTo("BML"),
                () -> assertThat(performance.getGenre()).isEqualTo(Genre.CONCERT),
                () -> assertThat(performance.getPeriod().startDate()).isEqualTo("2022-05-13"),
                () -> assertThat(performance.getPeriod().endDate()).isEqualTo("2022-05-15"),
                () -> assertThat(performance.getSeries()).isNull(),
                () -> assertThat(performance.getMinAvailableAge()).isEqualTo(10),
                () -> assertThat(performance.getReservationSites()).hasSize(2),
                () -> assertThat(performance.getReservationSites()).containsExactlyInAnyOrder(ReservationSite.YES24, ReservationSite.INTERPARK)
        );
    }

    @Test
    @DisplayName("extract series domain entity from performance request")
    void extractSeriesDomainEntity() {
        RegisterPerformanceRequest request = createRegisterPerformanceRequest();
        List<Series> series = mapper.extractSeriesDomainEntity(request);

        assertAll(
                () -> assertThat(series).hasSize(3),
                () -> assertThat(series).extracting("playDate", "playTime", "casts", "isPerformed")
                        .containsExactlyInAnyOrder(
                                Tuple.tuple(playDates.get(1), playTimes.get(1), casts.get(1), true),
                                Tuple.tuple(playDates.get(2), playTimes.get(1), casts.get(2), false),
                                Tuple.tuple(playDates.get(0), playTimes.get(0), casts.get(0), true)
                        ),
                () -> assertThat(series).extracting("seriesId", "performanceId")
                        .contains(Tuple.tuple(null, null))
        );
    }

    @Test
    @DisplayName("convert series domain entities to series jpa entities")
    void convertSeriesDomainEntitiesToJpaEntities() {
        List<Series> series = createSeries();
        Performance performance = createPerformance();
        PerformanceEntity performanceEntity = mapper.convertDomainEntityToJpaEntity(performance);
        List<SeriesEntity> seriesEntities = mapper.convertDomainEntitiesToJpaEntities(series, performanceEntity);

        assertAll(
                () -> assertThat(seriesEntities).hasSize(3),
                () -> assertThat(seriesEntities).extracting("playDate", "playTime", "casts", "isPerformed")
                        .containsExactlyInAnyOrder(
                                Tuple.tuple(playDates.get(1), playTimes.get(1), casts.get(1), true),
                                Tuple.tuple(playDates.get(2), playTimes.get(1), casts.get(2), false),
                                Tuple.tuple(playDates.get(0), playTimes.get(0), casts.get(0), true)
                        ),
                () -> assertThat(seriesEntities).extracting("seriesId")
                        .containsNull(),
                () -> assertThat(seriesEntities).extracting("performance")
                        .contains(performanceEntity)
        );
    }

    private Performance createPerformance() {
        return Performance.builder()
                .title("BML")
                .genre(Genre.CONCERT)
                .period(new Period(LocalDate.of(2022, 5, 13), LocalDate.of(2022, 5, 15)))
                .venue("올림픽 공원")
                .runTime(540)
                .minAvailableAge(10)
                .reservationSites(List.of(ReservationSite.INTERPARK, ReservationSite.YES24))
                .build();
    }

    private RegisterPerformanceRequest createRegisterPerformanceRequest() {
        return RegisterPerformanceRequest.builder()
                .title("BML")
                .genre("CONCERT")
                .startDate(LocalDate.of(2022, 5, 13))
                .endDate(LocalDate.of(2022, 5, 15))
                .venue("올림픽 공원")
                .series(createSeriesRequests())
                .runTime(540)
                .minAvailableAge(10)
                .reservationSites(List.of("INTERPARK", "YES24"))
                .build();
    }

    private List<SeriesRequest> createSeriesRequests() {
        return List.of(
                SeriesRequest.builder()
                        .dateTime(LocalDateTime.of(2022, 5, 13, 15, 0))
                        .casts(casts.get(0))
                        .isPerformed(true)
                        .build(),
                SeriesRequest.builder()
                        .dateTime(LocalDateTime.of(2022, 5, 14, 12, 0))
                        .casts(casts.get(1))
                        .isPerformed(true)
                        .build(),
                SeriesRequest.builder()
                        .dateTime(LocalDateTime.of(2022, 5, 15, 12, 0))
                        .casts(casts.get(2))
                        .isPerformed(false)
                        .build()
        );
    }

    private List<Series> createSeries() {
        return List.of(
                Series.builder()
                        .playDate(playDates.get(0))
                        .playTime(playTimes.get(0))
                        .casts(casts.get(0))
                        .isPerformed(true)
                        .build(),
                Series.builder()
                        .playDate(playDates.get(1))
                        .playTime(playTimes.get(1))
                        .casts(casts.get(1))
                        .isPerformed(true)
                        .build(),
                Series.builder()
                        .playDate(playDates.get(2))
                        .playTime(playTimes.get(1))
                        .casts(casts.get(2))
                        .isPerformed(false)
                        .build()
        );
    }
}
