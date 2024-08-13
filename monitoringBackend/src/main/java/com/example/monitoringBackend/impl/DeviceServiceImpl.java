package com.example.monitoringBackend.impl;

import com.example.monitoringBackend.dto.DeviceDto;
import com.example.monitoringBackend.model.Device;
import com.example.monitoringBackend.repo.DeviceRepo;
import com.example.monitoringBackend.service.DeviceService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DeviceServiceImpl implements DeviceService {
    private DeviceRepo deviceRepo;
    private ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    @Override
    public void deleteDevice(Long id) {
        deviceRepo.deleteById(id);
    }

    @Override
    public DeviceDto addDevice(DeviceDto deviceDto) {
        Device device = mapper.map(deviceDto, Device.class);
        device = deviceRepo.save(device);
        return mapper.map(device,DeviceDto.class);
    }

    @Override
    @Transactional
    public void deleteDeviceByUserId(Long userId) {
        List<Device> devices = deviceRepo.findByUserId(userId);
        if (devices.isEmpty()) {
            LOGGER.error("No devices found for user with id {}", userId);
        } else {
            for (Device device : devices) {
                LOGGER.info("Deleting device with id: {} for user id: {}", device.getDeviceId(), userId);
                deviceRepo.delete(device);
            }
        }
    }

    @Override
    public List<DeviceDto> findAllDevices() {
        List<Device> devices = deviceRepo.findAll();
        if(devices == null){
            return null;
        }
        Type listType = new TypeToken<List<DeviceDto>>(){}.getType();
        List<DeviceDto> deviceDtos = mapper.map(devices, listType);
        return deviceDtos;
    }
}
