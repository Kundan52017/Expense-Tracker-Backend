package com.example.eems.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @JsonProperty("read")
    private boolean isRead;  // To track whether the notification has been read
    private LocalDateTime timestamp;

    // Constructors
    public Notification() {
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    public Notification(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}