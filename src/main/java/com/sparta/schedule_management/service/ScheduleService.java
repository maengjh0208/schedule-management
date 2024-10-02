package com.sparta.schedule_management.service;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import com.sparta.schedule_management.dto.ScheduleResponseDto;
import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.entity.User;
import com.sparta.schedule_management.repository.ScheduleRepository;
import com.sparta.schedule_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        User user = userRepository.findByEmail(requestDto.getEmail());

        if (user == null) {
            user = userRepository.saveAndRetrieve(requestDto.getEmail());
        }

        Schedule savedSchedule = scheduleRepository.saveAndRetrieve(user.getUser_id(), schedule);

        return new ScheduleResponseDto(user, savedSchedule);
    }
}
