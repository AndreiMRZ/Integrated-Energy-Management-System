package com.example.monitoringBackend.repo;

import com.example.monitoringBackend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {

    List<Device> findByUserId(Long userId);
}
