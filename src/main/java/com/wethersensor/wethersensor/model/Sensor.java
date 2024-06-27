package com.wethersensor.wethersensor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "last_active_timestamp")
    private LocalDateTime lastActiveTimestamp;

}
