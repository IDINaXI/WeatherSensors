package com.wethersensor.wethersensor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private double value;
    @NotNull
    private boolean raining;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "sensors_id")
    private Sensor sensor;
}

