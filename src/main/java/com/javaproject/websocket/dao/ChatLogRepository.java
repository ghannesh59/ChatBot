package com.javaproject.websocket.dao;

import com.javaproject.websocket.entity.ChatLogger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLogger, String> {

}
