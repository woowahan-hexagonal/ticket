package com.jummi.ticket.performance.domain;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

public record Series(Long seriesId, Long performanceId, LocalDate playDate, LocalTime playTime, boolean isPerformed) {
    @Builder
    public Series {
    }
}
