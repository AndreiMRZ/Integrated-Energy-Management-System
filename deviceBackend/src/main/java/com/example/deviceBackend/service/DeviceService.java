package com.example.deviceBackend.service;

import com.example.deviceBackend.dto.DeviceDto;
import com.example.deviceBackend.model.Device;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface DeviceService {

    //DeviceDto addDevice(DeviceDto device);
    DeviceDto addDevice1(Device device, Long userId);

    List<DeviceDto> findAllDevices();

    DeviceDto findDeviceByName(String name);
    DeviceDto update(Long id, DeviceDto deviceDto);
    public void deleteDevice(String name);
}
