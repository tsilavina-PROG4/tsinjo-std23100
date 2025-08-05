package com.tsinjo.app.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donor {
	private Long id;
	private String name;
	private String email;
}
