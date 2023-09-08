package com.javaproject.websocket.service;

import com.javaproject.websocket.dao.ChatNodeRepository;
import com.javaproject.websocket.entity.ChatNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    @Autowired
    ChatNodeRepository chatNodeRepository;

    public static Map<Integer,String> nodeTextMap = new HashMap<>();
    public static Map<Integer,int[]> nodeAdjMap = new HashMap<>();
    public static Set<Integer> lastNode = new HashSet<>();

    @PostConstruct
    public void getAllNodes(){
        List<ChatNode> nodelist = chatNodeRepository.findAll();

        for(ChatNode node:nodelist){
            nodeTextMap.put(node.getId(), node.getText());
        }

        for(ChatNode node:nodelist){
          nodeAdjMap.put(node.getId(), node.getAdjacentNode());
       }

       for(ChatNode node : nodelist) {
           if(node.getLastQuestion() == true) {
               lastNode.add(node.getId());
           }
       }
    }
}
