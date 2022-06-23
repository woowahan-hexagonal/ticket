package com.jummi.ticket.performance.adapter.persistence;

import com.jummi.ticket.common.adapter.persistence.BaseEntity;
import com.jummi.ticket.performance.domain.Genre;
import com.jummi.ticket.performance.domain.ReservationSite;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "performance")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE performance SET is_deleted = true WHERE performance_id = ?")
@Entity
public class PerformanceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performanceId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate startDate;

    private LocalDate endDate;

    private String venue;

    private int runTime;

    private int minAvailableAge;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ReservationSite.class)
    @CollectionTable
    private Set<ReservationSite> reservationSites;
}
