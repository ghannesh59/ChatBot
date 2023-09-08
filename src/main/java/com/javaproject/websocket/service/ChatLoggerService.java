package com.javaproject.websocket.service;

import com.javaproject.websocket.dao.ChatLogRepository;
import com.javaproject.websocket.entity.ChatLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatLoggerService {

    @Autowired
    private ChatLogRepository chatLogRepository;
    private Map<String, List<ChatLogger> > loggerMap;

    public ChatLoggerService() {
        this.loggerMap = new HashMap<>();
    }

    public void saveChatLogInMap(String userId, Instant timeStamp, String message, String response) {
        ChatLogger chatLogger = new ChatLogger(userId, timeStamp,message,response);
        if(loggerMap.containsKey(userId)) {
            List<ChatLogger> loggerList = loggerMap.get(userId);
            loggerList.add(chatLogger);
            loggerMap.replace(userId, loggerList);
        } else {
            List arr = new ArrayList(); arr.add(chatLogger);
            loggerMap.put(userId, arr);
        }
    }

    public void insertChatLogs(String userName) {
        List<ChatLogger> ll = loggerMap.get(userName);
        chatLogRepository.saveAll(loggerMap.get(userName));
        loggerMap.remove(userName);
    }

    public ChatLogRepository getChatLogRepository() {
        return chatLogRepository;
    }

    public void setChatLogRepository(ChatLogRepository chatLogRepository) {
        this.chatLogRepository = chatLogRepository;
    }

    public Map<String, List<ChatLogger>> getLoggerMap() {
        return loggerMap;
    }

    public void setLoggerMap(Map<String, List<ChatLogger>> loggerMap) {
        this.loggerMap = loggerMap;
    }
}
