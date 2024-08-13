package com.example.monitoringBackend.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebSocketComp {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(String userId, String message) {
        messagingTemplate.convertAndSendToUser(userId, "/topic/notification", message);
    }
}
