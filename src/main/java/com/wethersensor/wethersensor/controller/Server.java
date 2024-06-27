package com.wethersensor.wethersensor.controller;

import com.wethersensor.wethersensor.model.Measurement;
import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.repository.MeasurementRepository;
import com.wethersensor.wethersensor.service.MeasurementService;
import com.wethersensor.wethersensor.service.SensorDataService;
import com.wethersensor.wethersensor.service.SensorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/sensors")
@AllArgsConstructor
public class Server {

    private final SensorService sensorService;
    private final SensorDataService sensorDataService;
    private final MeasurementService measurementService;

    @Autowired
    private final MeasurementRepository measurementRepository;

    @GetMapping
    public List<Sensor> findAllSensors() {
        return sensorService.findAllSensors();
    }

    @PostMapping("/start")
    public void startSensorDataSending() {
        sensorDataService.sendSensorDate();
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> saveSensor(@Valid @RequestBody Sensor sensor) {
        Sensor savedSensor = sensorService.saveSensor(sensor);
        return Map.of("key", savedSensor.get_uuid());
    }

    @GetMapping("/{name}")
    public Sensor findSensorByName(@PathVariable String name) {
        return sensorService.findSensorByName(name);
    }

    @PutMapping("/update_sensor")
    public Sensor updateSensor(@RequestBody Sensor sensor) {
        return sensorService.updateSensor(sensor);
    }

    @DeleteMapping("/delete_sensor/{name}")
    public void deleteSensor(@PathVariable String name) {
        sensorService.deleteSensor(name);
    }

    @PostMapping("/{key}/measurements")
    public Measurement saveMeasurement(@RequestBody Measurement measurement, @PathVariable String key) {
        return measurementService.saveMeasurement(measurement, key);
    }

    @PostMapping("/{key}/measure")
    public ResponseEntity<String> addMeasurement(@PathVariable("key") String key, @Valid @RequestBody Measurement measurement) {

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 15000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (measurement.getValue() < -100 || measurement.getValue() > 100) {
            return ResponseEntity.badRequest().body("Invalid value for temperature");
        }

        measurement.setTimestamp(LocalDateTime.now());
        measurementRepository.save(measurement);

        return ResponseEntity.status(HttpStatus.CREATED).body("Measurement added successfully");
    }
}
