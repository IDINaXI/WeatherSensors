package com.wethersensor.wethersensor.service.impl;

import com.wethersensor.wethersensor.model.Sensor;
import com.wethersensor.wethersensor.service.SensorDataService;
import com.wethersensor.wethersensor.service.SensorService;
import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class SensorDataSender implements SensorDataService {

    private final SensorService sensorService;
    @Override
    public void sendSensorDate() {
        List<Sensor> sensors = sensorService.findAllSensors();
        String serverUrl = "http://localhost:8080/api/v1/sensors/{key}/measurements";

        ExecutorService executorService = Executors.newFixedThreadPool(sensors.size() * 3);

        for (Sensor sensor : sensors) {
            executorService.submit(() -> {
                HttpClient httpClient = HttpClients.createDefault();
                while (true) {
                    try {
                        double randomValue = ThreadLocalRandom.current().nextDouble(-100, 100);
                        boolean isRaining = new Random().nextBoolean();

                        String sensorKey = sensor.get_uuid();

                        String json = String.format(Locale.ENGLISH, "{\"value\": %.2f, \"raining\": %b}", randomValue, isRaining);
                        System.out.println("Отправляемый JSON для сенсора " + sensorKey + ": " + json);

                        HttpPost request = new HttpPost(serverUrl.replace("{key}", sensorKey));
                        StringEntity params = new StringEntity(json, ContentType.APPLICATION_JSON);
                        request.setEntity(params);

                        HttpResponse response = httpClient.execute(request);
                        int statusCode = response.getStatusLine().getStatusCode();
                        System.out.println("Sensor " + sensorKey + " - HTTP Status Code: " + statusCode);

                        if (statusCode != 201) {
                            System.err.println("Ошибка при отправке данных для сенсора " + sensorKey + ": " + response.getStatusLine());
                            // Логирование тела ответа
                            HttpEntity entity = response.getEntity();
                            if (entity != null) {
                                String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                                System.err.println("Тело ответа: " + responseString);
                            }
                        }

                        int randomInterval = ThreadLocalRandom.current().nextInt(3000, 15000);
                        Thread.sleep(randomInterval);
                    } catch (IOException | InterruptedException e) {
                        System.err.println("Ошибка при выполнении HTTP-запроса: " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Непредвиденная ошибка: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
