package com.tsinjo.app.domain;

import java.time.Instant;
import lombok.*;

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
