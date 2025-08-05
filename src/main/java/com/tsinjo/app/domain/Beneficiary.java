package com.tsinjo.app.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {
	private Long id;
	private String name;
	private String email;
}
