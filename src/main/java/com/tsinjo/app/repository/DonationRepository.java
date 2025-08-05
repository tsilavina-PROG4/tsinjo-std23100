package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Donation;
import java.util.List;

public interface DonationRepository {
	Donation findById(Long id);

	List<Donation> findAll();

	Donation save(Donation donation);

	void deleteById(Long id);
}
