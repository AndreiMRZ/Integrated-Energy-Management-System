package com.example.deviceBackend.dto;

import com.example.deviceBackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DeviceDto {

        private Long id;
        private String name;
        private String description;
        private String address;
        private int consumption;
        private User user;
//        public DeviceDto(Long id, String name, String address, String description, int consumption, Long user_id) {
//                this.id = id;
//                this.name = name;
//                this.address = address;
//                this.description = description;
//                this.consumption = consumption;
//                this.user = new User(user_id);
//        }

}
