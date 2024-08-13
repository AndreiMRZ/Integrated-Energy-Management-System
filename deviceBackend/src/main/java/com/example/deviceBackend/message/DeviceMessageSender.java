package com.example.deviceBackend.message;

import com.example.deviceBackend.model.Device;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class DeviceMessageSender {

    private RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "amq.direct";
    private static final String ROUTING_KEY = "queuedevice";
    private ObjectMapper objectMapper;


    public void sendDeviceId(Long deviceId, Long userId) {
        Map<String, Long> messageMap = new HashMap<>();
        messageMap.put("deviceId", deviceId);
        messageMap.put("userId", userId);  // Add userId to the map

        // Convert the map to a JSON string
        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(messageMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Send the JSON string as the message
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, jsonMessage, m -> {
            m.getMessageProperties().setContentType("application/json");
            return m;
        });
    }


}
