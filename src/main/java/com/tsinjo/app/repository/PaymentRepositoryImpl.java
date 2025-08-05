package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
	private final JdbcTemplate jdbcTemplate;

	public PaymentRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final RowMapper<Payment> PAYMENT_ROW_MAPPER = new RowMapper<>() {
		@Override
		public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Payment.builder()
					.id(rs.getLong("id"))
					.pspPaymentId(rs.getString("psp_payment_id"))
					.payerEmail(rs.getString("payer_email"))
					.pspType(rs.getString("psp_type"))
					.status(rs.getString("status"))
					.creationInstant(rs.getTimestamp("creation_instant").toInstant())
					.lastVerificationInstant(rs.getTimestamp("last_verification_instant") != null
							? rs.getTimestamp("last_verification_instant").toInstant()
							: null)
					.amount(rs.getDouble("amount"))
					.build();
		}
	};

	@Override
	public Payment findById(Long id) {
		String sql = "SELECT * FROM payment WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, PAYMENT_ROW_MAPPER, id);
	}

	@Override
	public List<Payment> findAll() {
		String sql = "SELECT * FROM payment ORDER BY id DESC";
		return jdbcTemplate.query(sql, PAYMENT_ROW_MAPPER);
	}

	@Override
	public Payment save(Payment payment) {
		if (payment.getId() == null) {
			String sql = "INSERT INTO payment (psp_payment_id, payer_email, psp_type, status, creation_instant, last_verification_instant, amount) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
			Long id = jdbcTemplate.queryForObject(sql, Long.class,
					payment.getPspPaymentId(), payment.getPayerEmail(), payment.getPspType(), payment.getStatus(),
					payment.getCreationInstant(), payment.getLastVerificationInstant(), payment.getAmount());
			payment.setId(id);
		} else {
			String sql = "UPDATE payment SET psp_payment_id = ?, payer_email = ?, psp_type = ?, status = ?, creation_instant = ?, last_verification_instant = ?, amount = ? WHERE id = ?";
			jdbcTemplate.update(sql,
					payment.getPspPaymentId(), payment.getPayerEmail(), payment.getPspType(), payment.getStatus(),
					payment.getCreationInstant(), payment.getLastVerificationInstant(), payment.getAmount(),
					payment.getId());
		}
		return payment;
	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM payment WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
