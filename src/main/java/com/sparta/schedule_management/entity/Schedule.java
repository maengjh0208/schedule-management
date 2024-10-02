package com.sparta.schedule_management.entity;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import com.sparta.schedule_management.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private int schedule_id;
    private String title;
    private String description;
    private int user_id;
    private String author_name;
    private String encrypted_password;
    private int created_at;
    private int updated_at;

    public Schedule(ScheduleRequestDto requestDto) {
        author_name = requestDto.getUsername();
        title = requestDto.getTitle();
        description = requestDto.getDescription();
        encrypted_password = Util.encryptPassword(requestDto.getPassword());
    }
}
