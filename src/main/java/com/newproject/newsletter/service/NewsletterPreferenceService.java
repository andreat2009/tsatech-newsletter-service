package com.newproject.newsletter.service;

import com.newproject.newsletter.domain.NewsletterPreference;
import com.newproject.newsletter.dto.NewsletterPreferenceRequest;
import com.newproject.newsletter.dto.NewsletterPreferenceResponse;
import com.newproject.newsletter.events.EventPublisher;
import com.newproject.newsletter.repository.NewsletterPreferenceRepository;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsletterPreferenceService {
    private final NewsletterPreferenceRepository repository;
    private final EventPublisher eventPublisher;

    public NewsletterPreferenceService(NewsletterPreferenceRepository repository, EventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public NewsletterPreferenceResponse get(Long customerId) {
        NewsletterPreference pref = repository.findByCustomerId(customerId).orElseGet(() -> {
            NewsletterPreference created = new NewsletterPreference();
            created.setCustomerId(customerId);
            created.setSubscribed(false);
            created.setUpdatedAt(OffsetDateTime.now());
            return repository.save(created);
        });
        return toResponse(pref);
    }

    @Transactional
    public NewsletterPreferenceResponse upsert(Long customerId, NewsletterPreferenceRequest request) {
        NewsletterPreference pref = repository.findByCustomerId(customerId).orElseGet(() -> {
            NewsletterPreference created = new NewsletterPreference();
            created.setCustomerId(customerId);
            return created;
        });
        pref.setSubscribed(Boolean.TRUE.equals(request.getSubscribed()));
        pref.setUpdatedAt(OffsetDateTime.now());
        NewsletterPreference saved = repository.save(pref);

        NewsletterPreferenceResponse response = toResponse(saved);
        eventPublisher.publish("NEWSLETTER_PREFERENCE_UPDATED", "newsletter_preference", customerId.toString(), response);
        return response;
    }

    private NewsletterPreferenceResponse toResponse(NewsletterPreference pref) {
        NewsletterPreferenceResponse response = new NewsletterPreferenceResponse();
        response.setCustomerId(pref.getCustomerId());
        response.setSubscribed(pref.getSubscribed());
        response.setUpdatedAt(pref.getUpdatedAt());
        return response;
    }
}
