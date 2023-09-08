package com.javaproject.websocket.entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "chat_node")
public class ChatNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private String text;
    @Column(name = "adj_list")
    private int[] adjacentNode;
    @Column(name = "last_question")
    private Boolean lastQuestion;

    public ChatNode() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int[] getAdjacentNode() {
        return adjacentNode;
    }

    public void setAdjacentNode(int[] adjacentNode) {
        this.adjacentNode = adjacentNode;
    }

    public Boolean getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(Boolean lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    @Override
    public String toString() {
        return "ChatNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", adjacentNode=" + Arrays.toString(adjacentNode) +
                ", lastQuestion=" + lastQuestion +
                '}';
    }
}


