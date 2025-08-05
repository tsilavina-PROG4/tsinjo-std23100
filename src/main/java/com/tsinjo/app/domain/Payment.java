package com.tsinjo.app.domain;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	private Long id;
	private String pspPaymentId;
	private String payerEmail;
	private String pspType;
	private String status;
	private Instant creationInstant;
	private Instant lastVerificationInstant;
	private Double amount;
}
