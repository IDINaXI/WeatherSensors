package com.wethersensor.wethersensor.service.impl;

import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.repository.InMemorySensorDAO;
import com.wethersensor.wethersensor.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class InMemorySensorServiceImpl implements SensorService {

    public final InMemorySensorDAO repository;
    @Override
    public List<Sensor> findAllSensors(){
        return repository.findAllSensors();
    }

    @Override
    public Sensor saveSensor(Sensor sensor) {
        return repository.saveSensor(sensor);
    }

    @Override
    public Sensor findSensorByName(String name) {
        return repository.findSensorByName(name);
    }

    @Override
    public Sensor updateSensor(Sensor sensor) {
        return repository.updateSensor(sensor);
    }

    @Override
    public void deleteSensor(String name) {
        repository.deleteSensor(name);
    }

    @Override
    public Sensor findSensorBy_uuid(String _uuid) {
        return repository.findSensorByUuid(_uuid);
    }

    @Override
    public List<Sensor> findAllActiveSensors() {
        return List.of();
    }


}
