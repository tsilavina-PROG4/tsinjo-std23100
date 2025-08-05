package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Donor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DonorRepositoryImpl implements DonorRepository {
  private final JdbcTemplate jdbcTemplate;

  public DonorRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Donor> DONOR_ROW_MAPPER =
      new RowMapper<>() {
        @Override
        public Donor mapRow(ResultSet rs, int rowNum) throws SQLException {
          return Donor.builder()
              .id(rs.getLong("id"))
              .name(rs.getString("name"))
              .email(rs.getString("email"))
              .build();
        }
      };

  @Override
  public Donor findById(Long id) {
    String sql = "SELECT * FROM donor WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, DONOR_ROW_MAPPER, id);
  }

  @Override
  public List<Donor> findAll() {
    String sql = "SELECT * FROM donor ORDER BY id DESC";
    return jdbcTemplate.query(sql, DONOR_ROW_MAPPER);
  }

  @Override
  public Donor save(Donor donor) {
    if (donor.getId() == null) {
      String sql = "INSERT INTO donor (name, email) VALUES (?, ?) RETURNING id";
      Long id = jdbcTemplate.queryForObject(sql, Long.class, donor.getName(), donor.getEmail());
      donor.setId(id);
    } else {
      String sql = "UPDATE donor SET name = ?, email = ? WHERE id = ?";
      jdbcTemplate.update(sql, donor.getName(), donor.getEmail(), donor.getId());
    }
    return donor;
  }

  @Override
  public void deleteById(Long id) {
    String sql = "DELETE FROM donor WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}
