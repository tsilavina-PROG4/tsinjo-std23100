package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Payment;
import java.util.List;

public interface PaymentRepository {
  Payment findById(Long id);

  List<Payment> findAll();

  Payment save(Payment payment);

  void deleteById(Long id);
}
