package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Beneficiary;
import java.util.List;

public interface BeneficiaryRepository {
  Beneficiary findById(Long id);

  List<Beneficiary> findAll();

  Beneficiary save(Beneficiary beneficiary);

  void deleteById(Long id);
}
