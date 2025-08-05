package com.tsinjo.app.repository;

import com.tsinjo.app.domain.Help;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class HelpRepositoryImpl implements HelpRepository {
  private final JdbcTemplate jdbcTemplate;

  public HelpRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Help> HELP_ROW_MAPPER =
      new RowMapper<>() {
        @Override
        public Help mapRow(ResultSet rs, int rowNum) throws SQLException {
          return Help.builder()
              .id(rs.getLong("id"))
              .amount(rs.getDouble("amount"))
              .date(rs.getTimestamp("date").toInstant())
              // .beneficiary can be loaded separately if needed
              .build();
        }
      };

  @Override
  public Help findById(Long id) {
    String sql = "SELECT * FROM help WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, HELP_ROW_MAPPER, id);
  }

  @Override
  public List<Help> findAll() {
    String sql = "SELECT * FROM help ORDER BY date DESC";
    return jdbcTemplate.query(sql, HELP_ROW_MAPPER);
  }

  @Override
  public Help save(Help help) {
    if (help.getId() == null) {
      String sql = "INSERT INTO help (beneficiary_id, amount, date) VALUES (?, ?, ?) RETURNING id";
      Long id =
          jdbcTemplate.queryForObject(
              sql,
              Long.class,
              help.getBeneficiary() != null ? help.getBeneficiary().getId() : null,
              help.getAmount(),
              help.getDate());
      help.setId(id);
    } else {
      String sql = "UPDATE help SET beneficiary_id = ?, amount = ?, date = ? WHERE id = ?";
      jdbcTemplate.update(
          sql,
          help.getBeneficiary() != null ? help.getBeneficiary().getId() : null,
          help.getAmount(),
          help.getDate(),
          help.getId());
    }
    return help;
  }

  @Override
  public void deleteById(Long id) {
    String sql = "DELETE FROM help WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}
