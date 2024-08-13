package com.example.monitoringBackend.messages;

import com.example.monitoringBackend.dto.DeviceDto;
import com.example.monitoringBackend.model.Device;
import com.example.monitoringBackend.model.EnergyConsumption;
import com.example.monitoringBackend.repo.DeviceRepo;
import com.example.monitoringBackend.repo.EnergyConsumptionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@AllArgsConstructor
@Setter
@Getter
@Component
public class MessageDeviceReceiver {

    private EnergyConsumptionRepo energyConsumptionRepo;
    DeviceRepo deviceRepo;
    private ObjectMapper objectMapper;

@RabbitListener(queues = "queuedevice")
@Transactional
public void receiveDeviceMessage(Message message) {
    try {
        String messageJson = new String(message.getBody(), StandardCharsets.UTF_8);
        JsonNode jsonNode = objectMapper.readTree(messageJson);

        // Check if the deviceId and userId fields are present in the JSON
        if (!jsonNode.has("deviceId") || !jsonNode.has("userId")) {
            System.out.println("Message does not contain both deviceId and userId: " + messageJson);
            return;
        }

        Long deviceIdValue = jsonNode.get("deviceId").asLong();
        Long userId = jsonNode.get("userId").asLong();

        Device device = deviceRepo.findById(deviceIdValue).orElseGet(() -> {
            Device newDevice = new Device();
            newDevice.setDeviceId(deviceIdValue);
            newDevice.setUserId(userId);
            return newDevice;
        });

        deviceRepo.save(device);

    } catch (JsonProcessingException e) {
        System.out.println("Error parsing JSON message " );
        e.printStackTrace();
    } catch (Exception e) {
        System.out.println("Error handling the message: " + message);
        e.printStackTrace();
    }
}

}


