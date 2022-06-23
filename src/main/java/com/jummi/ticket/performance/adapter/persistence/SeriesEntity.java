package com.jummi.ticket.performance.adapter.persistence;

import com.jummi.ticket.common.adapter.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "series")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE series SET is_deleted = true WHERE series_id = ?")
@Entity
public class SeriesEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seriesId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    private LocalDate playDate;

    private LocalTime playTime;

    @ElementCollection
    @Column(name = "casts")
    private List<String> casts;

    private boolean isPerformed;
}
