package com.example.deviceBackend.controller;

import com.example.deviceBackend.dto.DeviceDto;
import com.example.deviceBackend.model.Device;
import com.example.deviceBackend.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@AllArgsConstructor
public class DeviceController {
    private DeviceService deviceService;

//    @PostMapping("/addDevice")
//    public ResponseEntity addDevice(@RequestBody DeviceDto deviceDto){
//        DeviceDto saveDto = deviceService.addDevice(deviceDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(saveDto);
//    }
    @PostMapping("/addDevice")
    public ResponseEntity addDevice(@RequestBody Device device, @RequestParam Long userId) {
        DeviceDto savedDevice = deviceService.addDevice1(device, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevice);
    }
    @GetMapping("/findDeviceByName/{name}")
    public ResponseEntity findDeviceByName(@PathVariable("name")String name){
        DeviceDto deviceDto = deviceService.findDeviceByName(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDto);
    }
    @Transactional
    @DeleteMapping("/deleteDevice/{name}")
    public ResponseEntity deleteDevice(@PathVariable("name") String name){
        deviceService.deleteDevice(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<DeviceDto>> findAll(){
        List<DeviceDto> deviceDtos = deviceService.findAllDevices();
        return ResponseEntity.ok().body(deviceDtos);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateDevice(@PathVariable Long id, @RequestBody DeviceDto deviceDto){
        DeviceDto deviceDto1 = deviceService.update(id, deviceDto);
        return ResponseEntity.status(HttpStatus.OK).body(deviceDto1);
    }
}

