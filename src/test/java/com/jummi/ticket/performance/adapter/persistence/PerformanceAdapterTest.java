package com.jummi.ticket.performance.adapter.persistence;

import com.jummi.ticket.performance.converter.PerformanceMapperImpl;
import com.jummi.ticket.performance.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PerformanceMapperImpl.class, PerformanceAdapter.class})
class PerformanceAdapterTest {

    @Autowired
    private PerformanceAdapter performanceAdapter;

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
    @DisplayName("save performance")
    void savePerformance() {
        Performance performance = createPerformance();
        List<Series> series = createSeries();
        Long performanceId = performanceAdapter.savePerformance(performance, series);

        assertThat(performanceId).isInstanceOf(Long.class);
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
