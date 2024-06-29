package com.wethersensor.wethersensor.controller;

import com.wethersensor.wethersensor.model.Measurement;
import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.repository.MeasurementRepository;
import com.wethersensor.wethersensor.service.MeasurementService;
import com.wethersensor.wethersensor.service.SensorDataService;
import com.wethersensor.wethersensor.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class Server {

    private final SensorService sensorService;
    private final SensorDataService sensorDataService;
    private final MeasurementService measurementService;

    @Autowired
    private final MeasurementRepository measurementRepository;

    @GetMapping
    @Operation(description = "Вывод всех активных сенсоров")
    @ApiResponses
    public ResponseEntity<?> getAllActiveSensors() {
        return ResponseEntity.ok().body(sensorService.getAllActiveSensors());
    }
    @GetMapping("/{key}/measurements")
    @Operation(description = "Вывод последних 20 записей сенсора")
    @ApiResponses
    public ResponseEntity<?> getLatestMeasurements(@PathVariable("key") String key) {
        try {
            return ResponseEntity.ok().body(measurementService.getLatestMeasurements(key));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/measurements")
    @Operation(description = "Вывод актуальной информации от всех сенсоров")
    @ApiResponses
    public ResponseEntity<?> getAllCurrentMeasurements() {
        try {
            return ResponseEntity.ok().body(measurementService.getAllCurrentMeasurements());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/{name}")
    @Operation(description = "Вывод сенсора по названию")
    @ApiResponses
    public Sensor findSensorByName(@PathVariable String name) {
        return sensorService.findSensorByName(name);
    }

    @PostMapping("/start")
    @Operation(description = "Симуляциях данных приходящих от сенсоров")
    @ApiResponses
    public void startSensorDataSending() {
        sensorDataService.sendSensorDate();
    }
    @PostMapping("/registration")
    @Operation(description = "Регистрация сенсора")
    @ApiResponses
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> saveSensor(@Valid @RequestBody Sensor sensor) {
        Sensor savedSensor = sensorService.saveSensor(sensor);
        return Map.of("key", savedSensor.getUuid());
    }
    @PostMapping("/{key}/measure")
    @Operation(description = "Сохранение данных от сенсора на сервер")
    @ApiResponses
    public ResponseEntity<String> addMeasurement(@PathVariable("key") String key, @Valid @RequestBody Measurement measurement) {

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 15000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (measurement.getValue() < -100 || measurement.getValue() > 100) {
            return ResponseEntity.badRequest().body("Некорректное значение для температуры");
        }

        measurement.setTimestamp(LocalDateTime.now());
        measurementRepository.save(measurement);

        return ResponseEntity.status(HttpStatus.CREATED).body("Сохранено успешно");
    }

    @PutMapping("/update_sensor")
    @Operation(description = "Обновление данных сессора")
    @ApiResponses
    public Sensor updateSensor(@RequestBody Sensor sensor) {
        return sensorService.updateSensor(sensor);
    }

    @DeleteMapping("/delete_sensor/{name}")
    @Operation(description = "Удаление данные сенсора по имени")
    @ApiResponses
    public void deleteSensor(@PathVariable String name) {
        sensorService.deleteSensor(name);
    }
}
