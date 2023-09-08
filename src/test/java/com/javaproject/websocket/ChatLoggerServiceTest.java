package com.javaproject.websocket;

import com.javaproject.websocket.dao.ChatLogRepository;
import com.javaproject.websocket.entity.ChatLogger;
import com.javaproject.websocket.service.ChatLoggerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class ChatLoggerServiceTest {


    private ChatLoggerService chatLoggerService;

    @Mock
    private ChatLogRepository chatLogRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chatLoggerService = new ChatLoggerService();
        chatLoggerService.setChatLogRepository(chatLogRepository);
    }

    @Test
    public void testSaveChatLogInMap_NewUser() {
        chatLoggerService.saveChatLogInMap("user1", Instant.now(), "Hello", "Hi");

        // Assert that the loggerMap contains the user and the chat log was added

        assert chatLoggerService.getLoggerMap().containsKey("user1");
        assert chatLoggerService.getLoggerMap().get("user1").size() == 1;
    }

    @Test
    public void testSaveChatLogInMap_ExistingUser() {
        chatLoggerService.saveChatLogInMap("user1", Instant.now(), "Message1", "Response1");
        chatLoggerService.saveChatLogInMap("user1", Instant.now(), "Message2", "Response2");

        // Assert that the chat logs were added to the existing user's log
        assert chatLoggerService.getLoggerMap().get("user1").size() == 2;
    }

    @Test
    public void testInsertChatLogs() {
        // Mock repository behavior
        when(chatLogRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        chatLoggerService.saveChatLogInMap("user1", Instant.now(), "Message1", "Response1");
        chatLoggerService.saveChatLogInMap("user1", Instant.now(), "Message2", "Response2");

        chatLoggerService.insertChatLogs("user1");

        // Verify that saveAll was called with the correct list
        verify(chatLogRepository, times(1)).saveAll(anyList());
    }
}