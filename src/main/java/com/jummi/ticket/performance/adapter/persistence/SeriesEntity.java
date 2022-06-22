package com.jummi.ticket.performance.adapter.persistence;

import com.jummi.ticket.common.adapter.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
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
    private Long performanceId;

    private LocalDate playDate;

    private LocalTime playTime;

    private boolean isPerformed;
}
