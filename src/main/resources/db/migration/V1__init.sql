-- Flyway migration: Initial schema for Tsinjo

CREATE TABLE donor (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE beneficiary (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    psp_payment_id VARCHAR(255) NOT NULL,
    payer_email VARCHAR(255) NOT NULL,
    psp_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    creation_instant TIMESTAMP NOT NULL,
    last_verification_instant TIMESTAMP,
    amount NUMERIC(12,2) NOT NULL
);

CREATE TABLE donation (
    id BIGSERIAL PRIMARY KEY,
    donor_id BIGINT REFERENCES donor(id),
    amount NUMERIC(12,2) NOT NULL,
    date TIMESTAMP NOT NULL,
    payment_id BIGINT REFERENCES payment(id)
);

CREATE TABLE help (
    id BIGSERIAL PRIMARY KEY,
    beneficiary_id BIGINT REFERENCES beneficiary(id),
    amount NUMERIC(12,2) NOT NULL,
    date TIMESTAMP NOT NULL
);
