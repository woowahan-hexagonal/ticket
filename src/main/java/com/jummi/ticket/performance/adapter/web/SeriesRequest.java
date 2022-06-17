package com.jummi.ticket.performance.adapter.web;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
public class SeriesRequest {
    @NotNull
    @DateTimeFormat(pattern = "yyyyMMddHHmm")
    private final LocalDateTime dateTime;

    private final boolean isPerformed;
}
