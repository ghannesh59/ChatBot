package com.javaproject.websocket.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "chat_logs")
public class ChatLogger {

    public ChatLogger() {

    }

    public ChatLogger(String userId, Instant timestamp, String message, String response) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.message = message;
        this.response = response;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "timestamp")
    private Instant timestamp;

    @Column(name = "message")
    private String message;

    @Column(name = "response")
    private String response;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "LoggerEntity{" +
                "userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
