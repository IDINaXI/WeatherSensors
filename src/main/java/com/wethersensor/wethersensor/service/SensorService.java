package com.wethersensor.wethersensor.service;

import com.wethersensor.wethersensor.model.Sensor;

import java.util.List;

public interface SensorService {
    List<Sensor> findAllSensors();
    Sensor saveSensor(Sensor sensor);
    Sensor findSensorByName(String name);
    Sensor updateSensor(Sensor sensor);
    void deleteSensor(String name);
    List<Sensor> getAllActiveSensors();
    void updateLastActiveTimestamp(String uuid);
}
