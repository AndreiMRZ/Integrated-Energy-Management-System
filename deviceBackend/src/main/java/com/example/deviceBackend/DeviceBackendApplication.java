package com.example.deviceBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.example.deviceBackend.repo")
public class DeviceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceBackendApplication.class, args);
	}

}
