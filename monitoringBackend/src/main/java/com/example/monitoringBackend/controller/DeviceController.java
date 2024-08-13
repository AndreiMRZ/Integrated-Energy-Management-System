package com.example.monitoringBackend.controller;

import com.example.monitoringBackend.impl.DeviceServiceImpl;
import com.example.monitoringBackend.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {
    private final DeviceService deviceService;

    @DeleteMapping("/userId={id}")
    public ResponseEntity deleteByDeviceId(@PathVariable("id") Long id){
            deviceService.deleteDeviceByUserId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
