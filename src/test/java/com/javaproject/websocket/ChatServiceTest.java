package com.javaproject.websocket;

import com.javaproject.websocket.dao.ChatNodeRepository;
import com.javaproject.websocket.entity.ChatNode;
import com.javaproject.websocket.service.ChatService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ChatServiceTest {
    @InjectMocks
    private ChatService chatService;

    @Mock
    private ChatNodeRepository chatNodeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllNodes() {
        List<ChatNode> mockNodes = new ArrayList<>();
        // Add mock nodes to simulate repository behavior
        // Construct mock nodes with necessary properties

        when(chatNodeRepository.findAll()).thenReturn(mockNodes);

        chatService.getAllNodes();

        // Add assertions here to verify that nodeTextMap, nodeAdjMap, and lastNode are populated correctly

        verify(chatNodeRepository, times(1)).findAll();
    }

}
