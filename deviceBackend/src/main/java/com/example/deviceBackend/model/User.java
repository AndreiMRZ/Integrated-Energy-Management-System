package com.example.deviceBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Setter
@Getter
@Builder
@NoArgsConstructor
public class User {

    @Id
    private Long userId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="userId")
    private List<Device> deviceList;


    public User(Long id) {
    }
}
