package com.sparta.schedule_management.repository;

import com.sparta.schedule_management.dto.ScheduleResponseDto;
import com.sparta.schedule_management.dto.ScheduleUpdateDto;
import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule findById(int schedule_id) {
        String sql = "SELECT * FROM schedule WHERE schedule_id = ? AND use_yn = 'Y'";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setSchedule_id(resultSet.getInt("schedule_id"));
                schedule.setTitle(resultSet.getString("title"));
                schedule.setDescription(resultSet.getString("description"));
                schedule.setUser_id(resultSet.getInt("user_id"));
                schedule.setAuthor_name(resultSet.getString("author_name"));
                schedule.setEncrypted_password(resultSet.getString("encrypted_password"));
                schedule.setCreated_at(Util.convertTimestampIntoInt(resultSet.getTimestamp("created_at")));
                schedule.setUpdated_at(Util.convertTimestampIntoInt(resultSet.getTimestamp("updated_at")));

                return schedule;
            } else {
                return null;
            }
        }, schedule_id);
    }

    public Schedule saveAndRetrieve(int userId, Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "  INSERT INTO schedule " +
                "       (title, description, user_id, author_name, encrypted_password) " +
                "       VALUES " +
                "       (?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, schedule.getTitle());
            preparedStatement.setString(2, schedule.getDescription());
            preparedStatement.setInt(3, userId);
            preparedStatement.setString(4, schedule.getAuthor_name());
            preparedStatement.setString(5, schedule.getEncrypted_password());

            return preparedStatement;
        }, keyHolder);

        return findById(keyHolder.getKey().intValue());
    }

    public List<ScheduleResponseDto> getScheduleInfos(String date, String username) {
        String sql = "SELECT *\n" +
                     "FROM schedule T101\n" +
                     "         INNER JOIN user T102 ON T102.user_id = T101.user_id\n" +
                     "WHERE DATE_FORMAT(T101.updated_at, '%Y-%m-%d') LIKE ?\n" +
                     "   OR T101.author_name = ?\n" +
                     "   AND T101.use_yn = 'Y'\n" +
                     "ORDER BY T101.updated_at DESC";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto();
                scheduleResponseDto.setUser_id(rs.getInt("user_id"));
                scheduleResponseDto.setSchedule_id(rs.getInt("schedule_id"));
                scheduleResponseDto.setUsername(rs.getString("author_name"));
                scheduleResponseDto.setEmail(rs.getString("email"));
                scheduleResponseDto.setTitle(rs.getString("title"));
                scheduleResponseDto.setDescription(rs.getString("description"));
                scheduleResponseDto.setCreated_at(Util.convertTimestampIntoInt(rs.getTimestamp("created_at")));
                scheduleResponseDto.setUpdated_at(Util.convertTimestampIntoInt(rs.getTimestamp("updated_at")));
                return scheduleResponseDto;
            }
        }, date, username);
    }

    public ScheduleResponseDto getScheduleInfo(int scheduleId) {
        String sql = "SELECT *\n" +
                     "FROM schedule T101\n" +
                     "         INNER JOIN user T102 ON T102.user_id = T101.user_id\n" +
                     "WHERE T101.schedule_id = ?\n" +
                     "   AND T101.use_yn = 'Y'";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto();
                scheduleResponseDto.setUser_id(resultSet.getInt("user_id"));
                scheduleResponseDto.setSchedule_id(resultSet.getInt("schedule_id"));
                scheduleResponseDto.setUsername(resultSet.getString("author_name"));
                scheduleResponseDto.setEmail(resultSet.getString("email"));
                scheduleResponseDto.setTitle(resultSet.getString("title"));
                scheduleResponseDto.setDescription(resultSet.getString("description"));
                scheduleResponseDto.setCreated_at(Util.convertTimestampIntoInt(resultSet.getTimestamp("created_at")));
                scheduleResponseDto.setUpdated_at(Util.convertTimestampIntoInt(resultSet.getTimestamp("updated_at")));
                return scheduleResponseDto;
            } else {
                return null;
            }
        }, scheduleId);
    }

    public void update(int scheduleId, ScheduleUpdateDto requestDto) {
        String sql = "UPDATE schedule SET author_name = ?, description = ? WHERE schedule_id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getDescription(), scheduleId);
    }

    public void delete(int scheduleId) {
        String sql = "UPDATE schedule SET use_yn = 'N' WHERE schedule_id = ?";
        jdbcTemplate.update(sql, scheduleId);
    }
}
