package com.jummi.ticket.performance.adapter.persistence;

import com.jummi.ticket.performance.domain.Genre;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "performance")
@Entity
public class PerformanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performanceId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate startDate;

    private LocalDate endDate;

    private String venue;

    @ElementCollection
    @CollectionTable(
            name = "series",
            joinColumns = @JoinColumn(name = "performance_id")
    )
    private List<SeriesEntity> series = new ArrayList<>();

    @ElementCollection
    @Column(name = "casts")
    private List<String> casts = new ArrayList<>();

    private int runTime;

    private int minAvailableAge;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(
            name = "reservation_sites",
            joinColumns = @JoinColumn(name = "performance_id")
    )
    private Set<ReservationSiteEntity> reservationSites = new HashSet<>();
}
