package com.jummi.ticket.performance.adapter.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeriesEntity {
    private Long seriesId;
    @Setter
    private Long performanceId;
    private LocalDate playDate;
    private LocalTime playTime;
    private boolean isPerformed;
}
