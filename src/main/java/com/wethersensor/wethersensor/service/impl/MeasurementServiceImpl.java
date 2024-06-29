package com.wethersensor.wethersensor.service.impl;

import com.wethersensor.wethersensor.model.Measurement;
import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.repository.MeasurementRepository;
import com.wethersensor.wethersensor.repository.SensorsRepository;
import com.wethersensor.wethersensor.service.MeasurementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorsRepository sensorsRepository;

    @Override
    @Transactional
    public Measurement saveMeasurement(Measurement measurement, String sensorUuid) {
        Sensor sensor = sensorsRepository.findSensorByUuid(sensorUuid);
        if (sensor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сенсор не найден");
        }

        measurement.setSensor(sensor);
        measurement.setTimestamp(LocalDateTime.now());
        return measurementRepository.save(measurement);
    }

    @Override
    public List<Measurement> getLatestMeasurements(String uuid) {
        Sensor sensor = sensorsRepository.findSensorByUuid(uuid);
        if (sensor == null) {
            throw new IllegalArgumentException("Сенсор с этим UUID '" + uuid + "' не найден");
        }

        return measurementRepository.findTop20BySensorOrderByTimestampDesc(sensor);

    }

    @Override
    public List<Measurement> getAllCurrentMeasurements() {
        LocalDateTime timeThreshold = LocalDateTime.now().minusMinutes(1);
        return measurementRepository.findCurrentMeasurements(timeThreshold);
    }

}

