package com.example.monitoringBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Device {

    @Id
    private Long deviceId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deviceId")
    private List<EnergyConsumption> energyConsumptionList;

    private Long userId;

    public Device(Long deviceId) {
    }
}
