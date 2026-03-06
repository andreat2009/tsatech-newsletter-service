CREATE TABLE newsletter_preference (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL UNIQUE,
    subscribed BOOLEAN NOT NULL DEFAULT FALSE,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_newsletter_customer_id ON newsletter_preference(customer_id);
