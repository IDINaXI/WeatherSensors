package com.wethersensor.wethersensor.repository;

import com.wethersensor.wethersensor.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorsRepository extends JpaRepository<Sensor, Long> {
    Sensor findSensorByName(String name);
    void deleteByName(String name);
    Sensor findSensorByUuid(String uuid);
    List<Sensor> findByActiveTrue();
}


