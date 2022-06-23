package com.jummi.ticket.performance.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationSite {
    INTERPARK("INTERPARK", "인터파크"),
    YES24("YES24", "예스24"),
    MELON("MELON TICKET", "멜론티켓"),
    ;

    private final String globalName;
    private final String koreanName;
}
