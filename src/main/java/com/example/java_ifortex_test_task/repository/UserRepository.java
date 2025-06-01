package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
        select u.*
        from users u
                inner join sessions s on u.id = s.user_id
        where device_type = :deviceType + 1
        order by s.started_at_utc desc
        """, nativeQuery = true)
    List<User> getUsersWithAtLeastOneMobileSession(@Param("deviceType") DeviceType deviceType);

    @Query(value = """
        select u.*
        from users u
        where u.id = (
            select user_id
            from sessions
            group by user_id
            order by count(*) desc
            limit 1)
        """, nativeQuery = true)
    User getUserWithMostSessions();
}
