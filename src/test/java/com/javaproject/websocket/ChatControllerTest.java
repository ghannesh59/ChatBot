package com.javaproject.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.javaproject.websocket.controller.ChatController;
import com.javaproject.websocket.model.ChatMessage;
import com.javaproject.websocket.service.ChatReqResService;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
class ChatControllerTest {

    public ChatController chatController;
    @Mock
    private ChatReqResService chatReqResService;
    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    public void setUp() {
        chatReqResService = mock(ChatReqResService.class);
        messagingTemplate = mock(SimpMessagingTemplate.class);
        chatController = new ChatController();
        chatController.chatReqResService = chatReqResService;
        chatController.messagingTemplate = messagingTemplate;
    }

    @Test
    public void testHandleChatMessage() {
        String userName = "testUser";
        ChatMessage message = new ChatMessage(userName, "Content");
        String nextQuestion = "";
        Integer questionId = 1;

        when(chatReqResService.getToNextChatNode(userName, message)).thenReturn(nextQuestion);
        when(chatReqResService.getCurrentQuestion(userName)).thenReturn(questionId);
        //when(chatReqResService.lastNodeContains(questionId)).thenReturn(true);

        chatController.handleChatMessage(userName, message);

        verify(messagingTemplate,times(1)).convertAndSend("/queue/messages/" + userName, nextQuestion);
    }

    @Test
    public void testStoreUsername() {
        String username = "testUser";
        String firstQuestion = "First question";
        ConcurrentHashMap<String,Integer> T1 = new ConcurrentHashMap<>();

        when(chatReqResService.getUserSessions()).thenReturn(T1);
        when(chatReqResService.storeUserName(username)).thenReturn(firstQuestion);

        ResponseEntity<String> response = chatController.storeUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstQuestion, response.getBody());
    }

    @Test
    public void testStoreUsernameEmpty() {
        ResponseEntity<String> response = chatController.storeUsername("");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDisconnectSession() {
        String userId = "testUserId";

        chatController.disconnectSession(userId);

        // Verify that the removeUserIdFromSession method is called in chatReqResService
        verify(chatReqResService).removeUserIdFromSession(userId);
    }



}