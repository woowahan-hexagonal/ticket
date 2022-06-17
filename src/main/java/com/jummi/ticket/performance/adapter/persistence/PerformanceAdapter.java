package com.jummi.ticket.performance.adapter.persistence;

import com.jummi.ticket.performance.application.port.out.SavePerformanceCommand;
import com.jummi.ticket.performance.converter.PerformanceMapper;
import com.jummi.ticket.performance.domain.Performance;
import com.jummi.ticket.performance.domain.Series;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PerformanceAdapter implements SavePerformanceCommand {
    private final PerformanceMapper mapper;
    private final PerformanceRepository performanceRepository;
    private final SeriesRepository seriesRepository;

    @Transactional
    @Override
    public Long savePerformance(Performance performance, List<Series> series) {
        PerformanceEntity performanceEntity = mapper.convertDomainEntityToJpaEntity(performance);
        PerformanceEntity saved = performanceRepository.save(performanceEntity);
        Long performanceId = saved.getPerformanceId();

        List<SeriesEntity> seriesEntities = series.stream()
                .map(mapper::convertDomainEntityToJpaEntity)
                .collect(Collectors.toList());
        seriesEntities.forEach(seriesEntity -> seriesEntity.setPerformanceId(performanceId));
        seriesRepository.saveAll(seriesEntities);

        return performanceId;
    }
}
