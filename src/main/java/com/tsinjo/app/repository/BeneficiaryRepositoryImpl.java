package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Beneficiary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BeneficiaryRepositoryImpl implements BeneficiaryRepository {
	private final JdbcTemplate jdbcTemplate;

	public BeneficiaryRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final RowMapper<Beneficiary> BENEFICIARY_ROW_MAPPER = new RowMapper<>() {
		@Override
		public Beneficiary mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Beneficiary.builder()
					.id(rs.getLong("id"))
					.name(rs.getString("name"))
					.email(rs.getString("email"))
					.build();
		}
	};

	@Override
	public Beneficiary findById(Long id) {
		String sql = "SELECT * FROM beneficiary WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, BENEFICIARY_ROW_MAPPER, id);
	}

	@Override
	public List<Beneficiary> findAll() {
		String sql = "SELECT * FROM beneficiary ORDER BY id DESC";
		return jdbcTemplate.query(sql, BENEFICIARY_ROW_MAPPER);
	}

	@Override
	public Beneficiary save(Beneficiary beneficiary) {
		if (beneficiary.getId() == null) {
			String sql = "INSERT INTO beneficiary (name, email) VALUES (?, ?) RETURNING id";
			Long id = jdbcTemplate.queryForObject(sql, Long.class, beneficiary.getName(), beneficiary.getEmail());
			beneficiary.setId(id);
		} else {
			String sql = "UPDATE beneficiary SET name = ?, email = ? WHERE id = ?";
			jdbcTemplate.update(sql, beneficiary.getName(), beneficiary.getEmail(), beneficiary.getId());
		}
		return beneficiary;
	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM beneficiary WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
