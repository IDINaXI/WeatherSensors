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

@Service
@AllArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorsRepository sensorsRepository;

    @Override
    @Transactional
    public Measurement saveMeasurement(Measurement measurement, String sensorUuid) {
        Sensor sensor = sensorsRepository.findSensorBy_uuid(sensorUuid);
        if (sensor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Сенсор не найден");
        }
        measurement.setSensor(sensor);
        measurement.setTimestamp(LocalDateTime.now());
        return measurementRepository.save(measurement);
    }

}

