package com.example.deviceBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String address;
    private int consumption;

    @ManyToOne
    @JsonIgnoreProperties("devices")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "userId")
    private User user;

}
