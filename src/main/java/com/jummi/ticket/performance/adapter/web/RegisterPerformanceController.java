package com.jummi.ticket.performance.adapter.web;

import com.jummi.ticket.performance.application.port.in.RegisterPerformanceCommand;
import com.jummi.ticket.performance.domain.PerformanceId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/performances")
@RestController
class RegisterPerformanceController {
    private final RegisterPerformanceCommand registerPerformanceCommand;

    @PostMapping
    ResponseEntity<Void> registerPerformance(@RequestBody @Valid RegisterPerformanceRequest request) {
        PerformanceId performanceId = registerPerformanceCommand.registerPerformance(request);

        return ResponseEntity.created(
                        URI.create("/api/performances/" + performanceId.id()))
                .build();
    }
}
