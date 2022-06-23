package com.jummi.ticket.performance.adapter.web;

import com.jummi.ticket.performance.application.port.in.RegisterPerformanceUseCase;
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
    private final RegisterPerformanceUseCase registerPerformanceUseCase;

    @PostMapping
    ResponseEntity<Void> registerPerformance(@RequestBody @Valid RegisterPerformanceRequest request) {
        Long performanceId = registerPerformanceUseCase.registerPerformance(request);

        return ResponseEntity.created(
                        URI.create("/api/performances/" + performanceId))
                .build();
    }
}
