package com.wethersensor.wethersensor.repository;

import com.wethersensor.wethersensor.model.Measurement;
import com.wethersensor.wethersensor.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findTop20BySensorOrderByTimestampDesc(Sensor sensor);

    @Query("SELECT m FROM Measurement m WHERE m.timestamp >= :timeThreshold")
    List<Measurement> findCurrentMeasurements(LocalDateTime timeThreshold);

    @Query("SELECT COUNT(m) > 0 FROM Measurement m WHERE m.sensor = :sensor AND m.timestamp > :timeThreshold")
    boolean existsBySensorAndTimestampAfter(Sensor sensor, LocalDateTime timeThreshold);

}
