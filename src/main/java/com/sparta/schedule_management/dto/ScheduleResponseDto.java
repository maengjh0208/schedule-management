package com.sparta.schedule_management.dto;

import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.entity.User;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private int user_id;
    private int schedule_id;
    private String username;
    private String email;
    private String title;
    private String description;
    private int created_at;
    private int updated_at;

    public ScheduleResponseDto(User user, Schedule schedule) {
        user_id = user.getUser_id();
        schedule_id = schedule.getSchedule_id();
        username = schedule.getAuthor_name();
        email = user.getEmail();
        title = schedule.getTitle();
        description = schedule.getDescription();
        created_at = schedule.getCreated_at();
        updated_at = schedule.getUpdated_at();
    }
}
