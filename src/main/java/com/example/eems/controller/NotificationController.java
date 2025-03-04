package com.example.eems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eems.model.Notification;
import com.example.eems.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ✅ Send Notification using JSON Body
    @PostMapping("/send")
    public Notification sendNotification(@RequestBody Notification notification) {
        return notificationService.sendNotification(notification);
    }

    // ✅ Get Unread Notifications
    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    // ✅ Mark Notification as Read
    @PutMapping("/mark-as-read/{id}")
    public void markAsRead(@PathVariable("id") Long id) {
        notificationService.markAsRead(id);
    }
    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

   
}
