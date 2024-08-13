package com.example.monitoringBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EnergyConsumption {
    //timestamp
    //device id
    //measurement value
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long timestamp;

    @JsonProperty("measurement_value")
    private double measurementValue;



    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "deviceId")
    private Device deviceId;

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", measurementValue=" + measurementValue +
                ", deviceId=" + deviceId.getDeviceId() +
                '}';
    }
}
