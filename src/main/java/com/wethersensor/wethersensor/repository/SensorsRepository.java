package com.wethersensor.wethersensor.repository;

import com.wethersensor.wethersensor.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorsRepository extends JpaRepository<Sensor, Long> {
    Sensor findSensorBy_name(String _name);
    void deleteBy_name(String _name);
    Sensor findSensorBy_uuid(String _uuid);
}


