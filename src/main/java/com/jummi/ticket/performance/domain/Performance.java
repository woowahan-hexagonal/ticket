package com.jummi.ticket.performance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Performance {
    private PerformanceId performanceId;
    private String title;
    private Genre genre;
    private Period period;
    private String venue;
    private List<Series> series;
    private int runTime;
    private int minAvailableAge;
    private List<ReservationSite> reservationSites;
}
