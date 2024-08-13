package com.example.monitoringBackend.impl;

import com.example.monitoringBackend.MonitoringBackendApplication;
import com.example.monitoringBackend.dto.EnergyConsumptionDto;
import com.example.monitoringBackend.model.EnergyConsumption;
import com.example.monitoringBackend.repo.EnergyConsumptionRepo;
import com.example.monitoringBackend.service.EnergyConsumptionService;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EnergyConsumptionServiceImpl implements EnergyConsumptionService {

    private final EnergyConsumptionRepo energyConsumptionRepo;
    private ModelMapper modelMapper;

    @Override
    public EnergyConsumptionDto addEnergy(MonitoringBackendApplication energyConsumptionDto) {
        EnergyConsumption energyConsumption = modelMapper.map(energyConsumptionDto, EnergyConsumption.class);
        energyConsumption = energyConsumptionRepo.save(energyConsumption);
        return modelMapper.map(energyConsumption, EnergyConsumptionDto.class);
    }

    @Override
    public void delete(Long id) {
        energyConsumptionRepo.deleteById(id);
    }
}
