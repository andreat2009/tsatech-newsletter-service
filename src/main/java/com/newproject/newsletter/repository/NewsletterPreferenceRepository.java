package com.newproject.newsletter.repository;

import com.newproject.newsletter.domain.NewsletterPreference;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterPreferenceRepository extends JpaRepository<NewsletterPreference, Long> {
    Optional<NewsletterPreference> findByCustomerId(Long customerId);
}
