package com.wethersensor.wethersensor.repository;

import com.wethersensor.wethersensor.model.Sensor;
import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
@AllArgsConstructor
@Repository
public class InMemorySensorDAO {
    private final List<Sensor> SENSORS = new ArrayList<>();

    public List<Sensor> findAllSensors() {
        return SENSORS;
    }


    public Sensor saveSensor(Sensor sensor) {
        SENSORS.add(sensor);
        return sensor;
    }


    public Sensor findSensorByName(String name) {
        return SENSORS.stream()
                .filter(element -> element.get_name().equals(name))
                .findFirst()
                .orElse(null);
    }


    public Sensor updateSensor(Sensor sensor) {
        var sensorIndex = IntStream.range(0, SENSORS.size())
                .filter(index -> SENSORS.get(index).get_name().equals(sensor.get_name()))
                .findFirst()
                .orElse(-1);
        if (sensorIndex > -1) {
            SENSORS.set(sensorIndex, sensor);
            return sensor;
        } else
            return null;

    }


    public void deleteSensor(String name) {
        var sensor = findSensorByName(name);
        if (sensor != null) {
            SENSORS.remove(sensor);
        }
    }

    public Sensor findSensorByUuid(String uuid) {
        Optional<Sensor> sensor = SENSORS.stream()
                .filter(s -> s.get_uuid().equals(uuid))
                .findFirst();
        return sensor.orElse(null);
    }


}
