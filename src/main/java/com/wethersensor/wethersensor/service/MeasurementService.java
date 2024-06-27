package com.wethersensor.wethersensor.service;

import com.wethersensor.wethersensor.model.Measurement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeasurementService {
    Measurement saveMeasurement(Measurement measurement, String sensorKey);
    List<Measurement> getLatestMeasurements(String uuid);
    List<Measurement> getAllCurrentMeasurements();
}
