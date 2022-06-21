package com.jummi.ticket.performance.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {
}
