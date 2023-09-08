package com.javaproject.websocket.controller;

import com.javaproject.websocket.model.ChatMessage;
import com.javaproject.websocket.service.ChatService;
import com.javaproject.websocket.service.ChatLoggerService;
import com.javaproject.websocket.service.ChatReqResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;

@Controller
public class ChatController {

    @Autowired
    public ChatReqResService chatReqResService;

    @Autowired
    public SimpMessagingTemplate messagingTemplate;

    @Autowired
    ChatLoggerService chatLoggerService;

    public ChatController() {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/{userName}")
    public void handleChatMessage(@DestinationVariable String userName, ChatMessage message) {
        String nextQuestion = chatReqResService.getToNextChatNode(userName, message);
        Integer q = chatReqResService.getCurrentQuestion(userName);
        messagingTemplate.convertAndSend("/queue/messages/"+userName, nextQuestion);
        if(ChatService.lastNode.contains(q)) {
            chatReqResService.saveLogInMap(userName, Instant.now(),"");
            disconnectSession(userName);
        }
    }

    @PostMapping("/storeUsername")
    public ResponseEntity<String> storeUsername(@RequestBody String username) {
        if (username == null || username.isEmpty()||chatReqResService.getUserSessions().containsKey(username)) {
            return new ResponseEntity<>("Username already taken", HttpStatus.OK);
        }
        String firstQuestion = chatReqResService.storeUserName(username);
        return new ResponseEntity<>(firstQuestion, HttpStatus.OK);
    }

    public void disconnectSession(String userId) {
        chatReqResService.removeUserIdFromSession(userId);
    }

}
