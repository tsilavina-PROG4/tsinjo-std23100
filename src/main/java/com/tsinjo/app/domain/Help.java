package com.tsinjo.app.domain;

import java.time.Instant;
import lombok.*;

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
