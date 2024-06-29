# Weather Sensor API

Этот проект представляет собой API для управления датчиками и измерениями в приложении для мониторинга погоды. 

## Оглавление
- [Описание](#описание)
- [Технологии](#технологии)
- [Требования](#требования)
- [Установка](#установка)
- [Запуск](#запуск)
- [API Эндпоинты](#api-эндпоинты)
- [Документация API](#документация-api)

# Описание

Weather Sensor API позволяет регистрировать сенсоры, собирать данные о погоде с этих сенсоров и просматривать собранные данные. API также поддерживает симуляцию данных от сенсоров для тестирования.

# Технологии

- Java 17
- Spring Boot 3.3.1
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Swagger
- SpringDoc OpenAPI

# Требования

- JDK 17 или выше
- Maven 3.6.0 или выше
- PostgreSQL 10 или выше

# Установка

1. Клонируйте репозиторий:
   git clone https://github.com/IDINaXI/WeatherSensors

2. Перейдите в директорию проекта:
   cd wethersensor
   
4. Установите зависимости и соберите проект с помощью Maven:
   mvn clean install

# Запуск

1. Запустите PostgreSQL и создайте базу данных:
   CREATE DATABASE wethersensor_db;

2. Обновите `application.properties` файл в `src/main/resources` с вашими настройками базы данных:
   spring.datasource.url=jdbc:postgresql://localhost:5432/wethersensor_db
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword

3. Запустите приложение:
   mvn spring-boot:run

# API Эндпоинты

Список активных сенсоров
**GET** `/api/v1/sensors`

Возвращает список всех активных сенсоров.

Последние 20 измерений сенсора
**GET** `/api/v1/sensors/{key}/measurements`

Возвращает последние 20 записей сенсора по его ключу.

Все текущие измерения
**GET** `/api/v1/sensors/measurements`

Возвращает актуальную информацию от всех сенсоров.

Найти сенсор по названию
**GET** `/api/v1/sensors/{name}`

Возвращает сенсор по его названию.

Запуск симуляции данных
**POST** `/api/v1/sensors/start`

Запускает симуляцию данных, приходящих от сенсоров.

Регистрация сенсора
**POST** `/api/v1/sensors/registration`

Регистрирует новый сенсор.

Сохранение данных от сенсора
**POST** `/api/v1/sensors/{key}/measure`

Сохраняет данные от сенсора на сервере.

Обновление данных сенсора
**PUT** `/api/v1/sensors/update_sensor`

Обновляет данные сенсора.

Удаление сенсора по имени
**DELETE** `/api/v1/sensors/delete_sensor/{name}`

Удаляет данные сенсора по его имени.

## Документация API

Документация для API доступна по следующему URL после запуска приложения:

```
http://localhost:8080/swagger-ui/index.html
```
