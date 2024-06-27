package com.wethersensor.wethersensor.repository;

import com.wethersensor.wethersensor.model.Measurement;
import com.wethersensor.wethersensor.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findTop20BySensorOrderByTimestampDesc(Sensor sensor);
}
