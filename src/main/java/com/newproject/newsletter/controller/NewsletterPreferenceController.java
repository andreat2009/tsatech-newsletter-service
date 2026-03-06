package com.newproject.newsletter.controller;

import com.newproject.newsletter.dto.NewsletterPreferenceRequest;
import com.newproject.newsletter.dto.NewsletterPreferenceResponse;
import com.newproject.newsletter.service.NewsletterPreferenceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/newsletter")
public class NewsletterPreferenceController {
    private final NewsletterPreferenceService service;

    public NewsletterPreferenceController(NewsletterPreferenceService service) {
        this.service = service;
    }

    @GetMapping
    public NewsletterPreferenceResponse get(@PathVariable Long customerId) {
        return service.get(customerId);
    }

    @PutMapping
    public NewsletterPreferenceResponse upsert(
        @PathVariable Long customerId,
        @Valid @RequestBody NewsletterPreferenceRequest request
    ) {
        return service.upsert(customerId, request);
    }
}
