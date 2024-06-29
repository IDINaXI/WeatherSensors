package com.wethersensor.wethersensor.controller;

import com.wethersensor.wethersensor.model.Measurement;
import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.repository.MeasurementRepository;
import com.wethersensor.wethersensor.service.MeasurementService;
import com.wethersensor.wethersensor.service.SensorDataService;
import com.wethersensor.wethersensor.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/sensors")
@AllArgsConstructor
@Api("Сервер для управления приложением")
public class Server {

    private final SensorService sensorService;
    private final SensorDataService sensorDataService;
    private final MeasurementService measurementService;

    @Autowired
    private final MeasurementRepository measurementRepository;

    @GetMapping
    @ApiOperation("Вывод всех активных сенсоров")
    public ResponseEntity<?> getAllActiveSensors() {
        return ResponseEntity.ok().body(sensorService.getAllActiveSensors());
    }
    @GetMapping("/{key}/measurements")
    @ApiOperation("Вывод последних 20 записей сенсора")
    public ResponseEntity<?> getLatestMeasurements(@PathVariable("key") String key) {
        try {
            return ResponseEntity.ok().body(measurementService.getLatestMeasurements(key));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/measurements")
    @ApiOperation("Вывод актуальной информации от всех сенсоров")
    public ResponseEntity<?> getAllCurrentMeasurements() {
        try {
            return ResponseEntity.ok().body(measurementService.getAllCurrentMeasurements());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/{name}")
    @ApiOperation("Вывод сенсора по названию")
    public Sensor findSensorByName(@PathVariable String name) {
        return sensorService.findSensorByName(name);
    }

    @PostMapping("/start")
    @ApiOperation("Симуляциях данных приходящих от сенсоров")
    public void startSensorDataSending() {
        sensorDataService.sendSensorDate();
    }
    @PostMapping("/registration")
    @ApiOperation("Регистрация сенсора")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> saveSensor(@Valid @RequestBody Sensor sensor) {
        Sensor savedSensor = sensorService.saveSensor(sensor);
        return Map.of("key", savedSensor.getUuid());
    }
    @PostMapping("/{key}/measure")
    @ApiOperation("Сохранение данных от сенсора на сервер")
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

    @PutMapping("/update_sensor")
    @ApiOperation("Обновление данных сессора")
    public Sensor updateSensor(@RequestBody Sensor sensor) {
        return sensorService.updateSensor(sensor);
    }

    @DeleteMapping("/delete_sensor/{name}")
    @ApiOperation("Удаление данные сенсора по имени")
    public void deleteSensor(@PathVariable String name) {
        sensorService.deleteSensor(name);
    }
}
