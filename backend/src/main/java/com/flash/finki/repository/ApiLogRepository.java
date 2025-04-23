package com.flash.finki.repository;

import com.flash.finki.model.ApiLog;
import com.flash.finki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {
    List<ApiLog> findByUser(User user);
}