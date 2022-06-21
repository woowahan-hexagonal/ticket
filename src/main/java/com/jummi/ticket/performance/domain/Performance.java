package com.jummi.ticket.performance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Performance {
    private PerformanceId performanceId;
    private String title;
    private Genre genre;
    private Period period;
    private String venue;
    private List<Series> series;
    private List<String> casts;
    private int runTime;
    private int minAvailableAge;
    private List<ReservationSite> reservationSites;
}
