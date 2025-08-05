package com.tsinjo.app.domain;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Help {
	private Long id;
	private Beneficiary beneficiary;
	private Double amount;
	private Instant date;
}
