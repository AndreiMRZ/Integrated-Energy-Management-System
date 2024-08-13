package com.example.monitoringBackend.messages;

import com.example.monitoringBackend.MonitoringBackendApplication;
import com.example.monitoringBackend.config.WebSocketComp;
import com.example.monitoringBackend.model.Device;
import com.example.monitoringBackend.model.EnergyConsumption;
import com.example.monitoringBackend.repo.DeviceRepo;
import com.example.monitoringBackend.repo.EnergyConsumptionRepo;
import com.example.monitoringBackend.service.EnergyConsumptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@Service
@NoArgsConstructor
public class MessageReceiver {

//    private EnergyConsumptionRepo energyConsumptionRepo;
//
//    @RabbitListener(queues = "queuecsv")
//    @Transactional
//    public void receiveMessage(EnergyConsumption reading) {
//        System.out.println(reading.getDeviceId());
//        energyConsumptionRepo.save(reading);
//    }

    @Autowired
    private EnergyConsumptionRepo energyConsumptionRepo;

    @Autowired
    private DeviceRepo deviceRepo;


    @Autowired
    @Lazy
    private WebSocketComp webSocketComp ;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final double maxValue = 10.0;
    private long startTime = System.currentTimeMillis();
    private long oneHourMilis = 2*1000;
    private double hourlyEnergy = 0.000;

    @RabbitListener(queues = "queuecsv")
    public void receiveMessage(Message message) {
        try { String messageJson = new String(message.getBody(), StandardCharsets.UTF_8);
            JsonNode jsonNode = objectMapper.readTree(messageJson);
            Long deviceIdValue = jsonNode.get("device_id").asLong();
            Double measurementValue = jsonNode.get("measurement_value").asDouble();
            Long timestamp = jsonNode.get("timestamp").asLong();

            Device device = deviceRepo.findById(deviceIdValue).orElseGet(() -> {
                Device newDevice = new Device();
                newDevice.setDeviceId(deviceIdValue);
                return deviceRepo.save(newDevice);
            });

            EnergyConsumption reading = new EnergyConsumption();
            reading.setDeviceId(device);
            reading.setMeasurementValue(measurementValue);
            reading.setTimestamp(timestamp);


            energyConsumptionRepo.save(reading);
            System.out.println(reading.toString());

            hourlyEnergy += measurementValue;
            if(System.currentTimeMillis() - startTime >= oneHourMilis){
                System.out.println("Hourly energy consume: " + hourlyEnergy);
            }
           // System.out.println("consum energie " + hourlyEnergy + " masuratori" + measurementValue);
            if(hourlyEnergy > maxValue){
                System.out.println("Consum mare");
                webSocketComp.sendNotificationToUser("2","Consum mare");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    }



