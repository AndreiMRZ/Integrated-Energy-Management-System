package com.example.monitoringBackend.service;

import com.example.monitoringBackend.MonitoringBackendApplication;
import com.example.monitoringBackend.dto.EnergyConsumptionDto;
import org.springframework.stereotype.Component;

@Component
public interface EnergyConsumptionService {
    EnergyConsumptionDto addEnergy(MonitoringBackendApplication energyConsumptionDto);
    public void delete(Long id);

}
