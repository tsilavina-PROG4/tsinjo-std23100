package com.tsinjo.app.domain;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationInput {
	private Long id;
	private String donorName;
	private String donorEmail;
	private Double amount;
}
