package com.wethersensor.wethersensor.service.impl;

import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.repository.SensorsRepository;
import com.wethersensor.wethersensor.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class SensorsServiceImpl implements SensorService {

    private final SensorsRepository repository;
    @Override
    public List<Sensor> findAllSensors() {
        return repository.findAll();
    }
    @Override
    public Sensor saveSensor(Sensor sensor) {
        if (repository.findSensorBy_name(sensor.get_name()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сенсор с таким названием уже существует");
        }
        else {
            sensor.set_uuid(UUID.randomUUID().toString());
            return repository.save(sensor);
        }
    }

    @Override
    public Sensor findSensorByName(String name) {
        return repository.findSensorBy_name(name);
    }

    @Override
    public Sensor updateSensor(Sensor sensor) {
        return repository.save(sensor);
    }

    @Override
    public void deleteSensor(String name) {
        repository.deleteBy_name(name);
    }

    @Override
    public Sensor findSensorBy_uuid(String _uuid) {
        return repository.findSensorBy_uuid(_uuid);
    }

    @Override
    public List<Sensor> findAllActiveSensors() {
        return List.of();
    }

}
