package com.sparta.schedule_management.repository;

import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule findById(int schedule_id) {
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";

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
}
