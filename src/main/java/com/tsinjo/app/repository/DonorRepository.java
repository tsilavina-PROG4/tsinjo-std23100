package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Donor;
import java.util.List;

public interface DonorRepository {
	Donor findById(Long id);

	List<Donor> findAll();

	Donor save(Donor donor);

	void deleteById(Long id);
}
