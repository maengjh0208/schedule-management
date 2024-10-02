package com.sparta.schedule_management.repository;

import com.sparta.schedule_management.entity.User;
import com.sparta.schedule_management.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setEmail(resultSet.getString("email"));
                return user;
            } else {
                return null;
            }
        }, email);
    }

    public User findById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setEmail(resultSet.getString("email"));
                user.setCreated_at(Util.convertTimestampIntoInt(resultSet.getTimestamp("created_at")));
                user.setUpdated_at(Util.convertTimestampIntoInt(resultSet.getTimestamp("updated_at")));

                return user;
            } else {
                return null;
            }
        }, userId);
    }

    public User saveAndRetrieve(String email) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO user (email) VALUES (?)";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, email);
            return preparedStatement;
        }, keyHolder);


        return findById(keyHolder.getKey().intValue());
    }
}
