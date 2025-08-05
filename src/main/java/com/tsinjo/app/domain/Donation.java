package com.tsinjo.app.domain;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
	private Long id;
	private Donor donor;
	private Double amount;
	private Instant date;
	private Payment payment;
}
