package com.example.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class SmartMetering {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;  // Inject ObjectMapper

    private static final String SENSOR_CSV_FILE = "sensor.csv";
    private static final String EXCHANGE_NAME = "amq.direct";
    private static final String ROUTING_KEY = "queuecsv";
    private static final String DEVICE_ID = "404";

    @PostConstruct
    public void startSimulation() {
        simulateSmartMeter();
    }

    private void simulateSmartMeter() {
        new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new FileReader(SENSOR_CSV_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    double measurementValue = Double.parseDouble(line);
                    long timestamp = Instant.now().toEpochMilli();
                    Map<String, Object> message = new HashMap<>();
                    message.put("timestamp", timestamp);
                    message.put("device_id", 252);
                    message.put("measurement_value", measurementValue);

                    String jsonMessage = objectMapper.writeValueAsString(message);  // Serialize map to JSON

                    rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, jsonMessage, m -> {
                        m.getMessageProperties().setContentType("application/json");
                        return m;
                    });

                    System.out.println(jsonMessage);
                    Thread.sleep(5000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
