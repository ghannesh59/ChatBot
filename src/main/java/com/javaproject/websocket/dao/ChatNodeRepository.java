package com.javaproject.websocket.dao;

import com.javaproject.websocket.entity.ChatNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatNodeRepository extends JpaRepository<ChatNode,Integer> {

}
