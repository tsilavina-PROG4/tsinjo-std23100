package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Donation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DonationRepositoryImpl implements DonationRepository {
	private final JdbcTemplate jdbcTemplate;

	public DonationRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final RowMapper<Donation> DONATION_ROW_MAPPER = new RowMapper<>() {
		@Override
		public Donation mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Donation.builder()
					.id(rs.getLong("id"))
					.amount(rs.getDouble("amount"))
					.date(rs.getTimestamp("date").toInstant())
					// .donor and .payment can be loaded separately if needed
					.build();
		}
	};

	@Override
	public Donation findById(Long id) {
		String sql = "SELECT * FROM donation WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, DONATION_ROW_MAPPER, id);
	}

	@Override
	public List<Donation> findAll() {
		String sql = "SELECT * FROM donation ORDER BY date DESC";
		return jdbcTemplate.query(sql, DONATION_ROW_MAPPER);
	}

	@Override
	public Donation save(Donation donation) {
		if (donation.getId() == null) {
			String sql = "INSERT INTO donation (donor_id, amount, date, payment_id) VALUES (?, ?, ?, ?) RETURNING id";
			Long id = jdbcTemplate.queryForObject(sql, Long.class,
					donation.getDonor() != null ? donation.getDonor().getId() : null,
					donation.getAmount(), donation.getDate(),
					donation.getPayment() != null ? donation.getPayment().getId() : null);
			donation.setId(id);
		} else {
			String sql = "UPDATE donation SET donor_id = ?, amount = ?, date = ?, payment_id = ? WHERE id = ?";
			jdbcTemplate.update(sql,
					donation.getDonor() != null ? donation.getDonor().getId() : null,
					donation.getAmount(), donation.getDate(),
					donation.getPayment() != null ? donation.getPayment().getId() : null,
					donation.getId());
		}
		return donation;
	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM donation WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
