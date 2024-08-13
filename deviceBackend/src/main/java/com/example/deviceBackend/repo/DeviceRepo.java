package com.example.deviceBackend.repo;

import com.example.deviceBackend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepo extends JpaRepository<Device,Long> {
    Optional<Device> findByName(String name);
    Device findDeviceByName(String name);
    public void deleteDeviceByName(String name);
}
