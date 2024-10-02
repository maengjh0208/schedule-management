package com.sparta.schedule_management.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String username;
    private String email;
    private String password;
    private String title;
    private String description;
}
