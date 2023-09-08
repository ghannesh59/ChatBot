package com.javaproject.websocket.service;

import com.javaproject.websocket.model.ChatMessage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatReqResService {

    @Getter
    private final ConcurrentHashMap<String, Integer> userSessions;

    Map<Integer,String> nodeTextMap = ChatService.nodeTextMap;
    Map<Integer,int[]> nodeAdjMap = ChatService.nodeAdjMap;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    ChatLoggerService chatLoggerService;

    @Autowired
    public ChatReqResService(SimpMessagingTemplate messagingTemplate) {
        this.userSessions = new ConcurrentHashMap<>();
    }

    public String getToNextChatNode(String userName, ChatMessage message) {
        if(!userSessions.containsKey(userName)) {
            return null;
        }

        int[] arr = nodeAdjMap.get(userSessions.get(userName));

        Integer userNode = 0;
        for(int n : arr) {
            if(nodeTextMap.get(n).equalsIgnoreCase(message.getContent())) {
                userNode = n;
                break;
            }
        }
        if(userNode==0) {
            String next = nodeTextMap.get(14)+'\n'+ nodeTextMap.get(userSessions.get(userName));
            return next;
        }
        Integer nextNode = nodeAdjMap.get(userNode)[0];
        Instant timeStamp = Instant.now();
        saveLogInMap(userName,timeStamp,nodeTextMap.get(userNode));
        userSessions.put(userName,nextNode);
        String next = nodeTextMap.get(nextNode);
        if (userName != null) {
            return next;
        }
        return next;
    }

    public String storeUserName(String userName) {
        userSessions.put(userName,1);
        return ChatService.nodeTextMap.get(1);
    }

    public void removeUserIdFromSession(String userName) {
        chatLoggerService.insertChatLogs(userName);
        userSessions.remove(userName);
    }

    public Integer getCurrentQuestion(String userName) {
        return userSessions.get(userName);
    }

    public void saveLogInMap(String userName, Instant timeStamp, String response) {
        chatLoggerService.saveChatLogInMap(userName,timeStamp,nodeTextMap.get(userSessions.get(userName)), response);
    }

    public ConcurrentHashMap<String, Integer> getUserSessions() {
        return userSessions;
    }

    public ChatLoggerService getChatLoggerService() {
        return chatLoggerService;
    }

    public void setChatLoggerService(ChatLoggerService chatLoggerService) {
        this.chatLoggerService = chatLoggerService;
    }
}