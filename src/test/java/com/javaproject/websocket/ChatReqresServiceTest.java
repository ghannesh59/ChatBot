package com.javaproject.websocket;

import com.javaproject.websocket.model.ChatMessage;
import com.javaproject.websocket.service.ChatLoggerService;
import com.javaproject.websocket.service.ChatReqResService;
import com.javaproject.websocket.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.*;

public class ChatReqresServiceTest {


    private ChatReqResService chatReqResService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chatReqResService = new ChatReqResService(messagingTemplate);

    }

    @Test
    public void testGetToNextChatNode_ExistingNode() {
        String userName = "user1";
        int userNode = 1;
        String content = "Message content";

        String sender = "Message Sender";

        Map<Integer, String> nodeTextMap = Collections.singletonMap(userNode, content);
        Map<Integer, int[]> nodeAdjMap = Collections.singletonMap(userNode, new int[] {2});

        ChatService.nodeTextMap = nodeTextMap;
        ChatService.nodeAdjMap = nodeAdjMap;


        ChatMessage message = new ChatMessage(content, sender);

        chatReqResService.getToNextChatNode(userName, message);


    }

    @Test
    public void testStoreUserName() {
        String userName = "user1";

        chatReqResService.storeUserName(userName);

        assert chatReqResService.getCurrentQuestion(userName) == 1;
    }


}
