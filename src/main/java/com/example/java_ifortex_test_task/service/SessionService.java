package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private static final LocalDateTime ENDED_BEFORE_2025 =
        LocalDateTime.of(2025, 1, 1, 0, 0, 0);

    // Returns the first (earliest) desktop Session
    public SessionResponseDTO getFirstDesktopSession() {
        return sessionMapper.toDto(
            sessionRepository.getFirstSessionByDeviceType(DeviceType.DESKTOP));
    }

    // Returns only Sessions from Active users that were ended before 2025
    public List<SessionResponseDTO> getSessionsFromActiveUsersEndedBefore2025() {
        return sessionRepository.getSessionsFromActiveUsersEndedBefore2025(ENDED_BEFORE_2025)
            .stream()
            .map(sessionMapper::toDto)
            .toList();
    }
}