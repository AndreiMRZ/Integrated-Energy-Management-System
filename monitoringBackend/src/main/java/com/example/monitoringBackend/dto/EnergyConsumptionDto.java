package com.example.monitoringBackend.dto;

import com.example.monitoringBackend.model.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EnergyConsumptionDto {

        private Long id;
        private Long timestamp;
        private double measurementValue;
        private Device device;
}
