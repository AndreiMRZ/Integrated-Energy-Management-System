package com.example.monitoringBackend.repo;

import com.example.monitoringBackend.model.Device;
import com.example.monitoringBackend.model.EnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyConsumptionRepo extends JpaRepository<EnergyConsumption, Long> {
    //public void deleteById(Long id);

}
