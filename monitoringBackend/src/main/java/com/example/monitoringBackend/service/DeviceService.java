package com.example.monitoringBackend.service;

import com.example.monitoringBackend.dto.DeviceDto;
import com.example.monitoringBackend.model.Device;

import java.util.List;

public interface DeviceService {

    public void deleteDevice(Long id);
    DeviceDto addDevice(DeviceDto device);

    public void deleteDeviceByUserId(Long userId);

    List<DeviceDto> findAllDevices();
}
