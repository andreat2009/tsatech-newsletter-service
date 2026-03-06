package com.newproject.newsletter.dto;

import jakarta.validation.constraints.NotNull;

public class NewsletterPreferenceRequest {
    @NotNull
    private Boolean subscribed;

    public Boolean getSubscribed() { return subscribed; }
    public void setSubscribed(Boolean subscribed) { this.subscribed = subscribed; }
}
