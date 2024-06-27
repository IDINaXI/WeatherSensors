package com.wethersensor.wethersensor.service.impl;

import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.repository.MeasurementRepository;
import com.wethersensor.wethersensor.repository.SensorsRepository;
import com.wethersensor.wethersensor.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class SensorsServiceImpl implements SensorService {

    private final SensorsRepository repository;
    private final MeasurementRepository measurementRepository;
    @Override
    public List<Sensor> findAllSensors() {
        return repository.findAll();
    }
    @Override
    public Sensor saveSensor(Sensor sensor) {
        if (repository.findSensorByName(sensor.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sensor with name " + sensor.getName() + " already exists");
        }
        else {
            sensor.setUuid(UUID.randomUUID().toString());
            return repository.saveAndFlush(sensor);
        }
    }

    @Override
    public Sensor findSensorByName(String name) {
        return repository.findSensorByName(name);
    }

    @Override
    public Sensor updateSensor(Sensor sensor) {
        return repository.saveAndFlush(sensor);
    }

    @Override
    public void deleteSensor(String name) {
        repository.deleteByName(name);
    }

    @Override
    public List<Sensor> getAllActiveSensors() {
        return repository.findByActiveTrue();
    }

    @Override
    public void updateLastActiveTimestamp(String uuid) {
        Sensor sensor = repository.findSensorByUuid(uuid);
        if (sensor != null) {
            sensor.setLastActiveTimestamp(LocalDateTime.now());
            repository.saveAndFlush(sensor);
        }
    }
    @Scheduled(fixedRate = 1000)
    public void checkSensorActivity() {
        List<Sensor> sensors = repository.findAll();
        LocalDateTime timeThreshold = LocalDateTime.now().minusMinutes(1);

        for (Sensor sensor : sensors) {
            boolean isActive = measurementRepository.existsBySensorAndTimestampAfter(sensor, timeThreshold);
            sensor.setActive(isActive);
            repository.saveAndFlush(sensor);
        }
    }

}
