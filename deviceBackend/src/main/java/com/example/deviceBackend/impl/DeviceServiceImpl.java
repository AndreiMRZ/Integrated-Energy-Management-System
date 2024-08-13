package com.example.deviceBackend.impl;

import com.example.deviceBackend.dto.DeviceDto;
import com.example.deviceBackend.message.DeviceMessageSender;
import com.example.deviceBackend.message.UserMessageSender;
import com.example.deviceBackend.model.Device;
import com.example.deviceBackend.model.User;
import com.example.deviceBackend.repo.DeviceRepo;
import com.example.deviceBackend.repo.UserRepo;
import com.example.deviceBackend.service.DeviceService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepo deviceRepo;
    private final UserRepo userRepo;
    private ModelMapper mapper;
    private DeviceMessageSender deviceMessageSender;
    private UserMessageSender userMessageSender;


//    public DeviceDto addDevice(DeviceDto deviceDto) {
//        Device device = mapper.map(deviceDto, Device.class);
//        device = deviceRepo.save(device);
//       // System.out.println("id de la dto " + deviceDto.getUser().getUserId());
//        return mapper.map(device,DeviceDto.class);
//    }
    @Override
    public DeviceDto addDevice1(Device device, Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if(user!=null){
            device.setUser(user);
            device = deviceRepo.save(device);
            deviceMessageSender.sendDeviceId(device.getId(),user.getUserId()); //iau id-ul
            //userMessageSender.sendUserId(user.getUserId());
            return mapper.map(device, DeviceDto.class);
        }
    return null;
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

    @Override
    public DeviceDto findDeviceByName(String name) {
        Device device = deviceRepo.findDeviceByName(name);
        if(device == null){
            return null;
        }
        DeviceDto deviceDto = mapper.map(device,DeviceDto.class);
        return deviceDto;
    }

    @Override
    public DeviceDto update(Long id, DeviceDto deviceDto) {
        if(deviceDto == null){
            throw new IllegalArgumentException("DeviceDto parameter cannot be null");
        }
        Optional<Device> optionalDevice = deviceRepo.findById(id);
       Device device = optionalDevice.get();
       if(deviceDto.getName() != null){
           device.setName(deviceDto.getName());
       }
       if(deviceDto.getAddress()!=null){
           device.setAddress(deviceDto.getAddress());
       }
       if(deviceDto.getConsumption()!=0){
           device.setConsumption(deviceDto.getConsumption());
       }
       if(deviceDto.getDescription()!=null){
           device.setDescription(deviceDto.getDescription());
       }
       device = deviceRepo.save(device);
       return mapper.map(device, DeviceDto.class);
    }

    @Override
    public void deleteDevice(String name) {
        deviceRepo.deleteDeviceByName(name);
    }
}
