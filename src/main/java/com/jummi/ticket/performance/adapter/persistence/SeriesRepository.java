package com.jummi.ticket.performance.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SeriesRepository extends JpaRepository<SeriesEntity, Long> {
}
