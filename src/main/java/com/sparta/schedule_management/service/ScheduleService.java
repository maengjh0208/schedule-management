package com.sparta.schedule_management.service;

import com.sparta.schedule_management.dto.ScheduleRequestDto;
import com.sparta.schedule_management.dto.ScheduleResponseDto;
import com.sparta.schedule_management.dto.ScheduleUpdateDto;
import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.entity.User;
import com.sparta.schedule_management.repository.ScheduleRepository;
import com.sparta.schedule_management.repository.UserRepository;
import com.sparta.schedule_management.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

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

    public List<ScheduleResponseDto> getSchedules(String date, String username) {
        return scheduleRepository.getScheduleInfos(date, username);
    }

    public ScheduleResponseDto getSchedule(int schedule_id) {
        return scheduleRepository.getScheduleInfo(schedule_id);
    }

    public ScheduleResponseDto updateSchedule(int schedule_id, ScheduleUpdateDto requestDto) {
        Schedule schedule = scheduleRepository.findById(schedule_id);

        if (schedule == null) {
            throw new NoSuchElementException("해당 id의 일정 게시판이 존재하지 않습니다.");
        }

        if (!Util.checkPassword(requestDto.getPassword(), schedule.getEncrypted_password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.update(schedule_id, requestDto);

        return scheduleRepository.getScheduleInfo(schedule_id);
    }
}
