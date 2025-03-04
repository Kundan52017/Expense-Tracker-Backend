package com.example.eems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eems.model.Notification;
import com.example.eems.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // ✅ Save Notification using JSON Body
    public Notification sendNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
    public List<Notification> getUnreadNotifications() {
        List<Notification> unreadList = notificationRepository.findByIsReadFalse();
        System.out.println("Unread Notifications from DB: " + unreadList); // 🔍 Debug log
        return unreadList;
    }
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
