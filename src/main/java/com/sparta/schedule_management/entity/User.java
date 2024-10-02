package com.sparta.schedule_management.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private int user_id;
    private String email;
    private int created_at;
    private int updated_at;
}
