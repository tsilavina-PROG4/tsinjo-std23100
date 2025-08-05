package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Help;
import java.util.List;

public interface HelpRepository {
	Help findById(Long id);

	List<Help> findAll();

	Help save(Help help);

	void deleteById(Long id);
}
