package com.jummi.ticket.performance.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class RegisterPerformanceRequest {
    @NotBlank
    private final String title;

    @NotBlank
    private final String genre;

    @NotBlank
    @DateTimeFormat(pattern = "yyyyMMdd")
    private final LocalDate startDate;

    @NotBlank
    @DateTimeFormat(pattern = "yyyyMMdd")
    private final LocalDate endDate;

    @NotBlank
    private final String venue;

    @NotNull
    private final List<SeriesRequest> series;

    @NotBlank
    private final List<String> casts;

    @Positive
    private final int runTime;

    @Positive
    private final int minAvailableAge;

    @NotNull
    private final List<String> reservationSites;
}
