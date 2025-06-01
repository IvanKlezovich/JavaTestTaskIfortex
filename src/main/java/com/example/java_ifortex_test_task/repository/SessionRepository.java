package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionRepository extends JpaRepository<Session, Long> {

  @Query(value = """
      select *
      from sessions
      where device_type = :deviceType
      order by started_at_utc
      limit 1
      """, nativeQuery = true)
  Session getFirstSessionByDeviceType(@Param("deviceType") DeviceType deviceType);

  @Query(value = """
      select s.id, s.ended_at_utc,
              s.started_at_utc,
              s.user_id,
                (case s.device_type
                        when 1 then 0
                        when 2 then 1
                    end) as device_type
              from sessions s
              join users u on s.user_id = u.id
              where u.deleted = false
              and s.ended_at_utc is not null
              and s.ended_at_utc < :endDate
              order by s.started_at_utc desc
      """, nativeQuery = true)
  List<Session> getSessionsFromActiveUsersEndedBefore2025(@Param("endDate") LocalDateTime endDate);
}