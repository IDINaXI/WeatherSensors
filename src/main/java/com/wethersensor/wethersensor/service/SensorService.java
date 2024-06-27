package com.wethersensor.wethersensor.service;

import com.wethersensor.wethersensor.model.Sensor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface SensorService {
    List<Sensor> findAllSensors();
    Sensor saveSensor(Sensor sensor);
    Sensor findSensorByName(String name);
    Sensor updateSensor(Sensor sensor);
    void deleteSensor(String name);
    Sensor findSensorBy_uuid(String _uuid);
    List<Sensor> findAllActiveSensors();
}
