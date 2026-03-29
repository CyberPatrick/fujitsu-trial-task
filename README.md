# Bolt Food Delivery Fee Calculator

## Overview

This project implements a sub-functionality of a food delivery application that calculates courier delivery fees based on:

* city
* vehicle type
* weather conditions
* optional timestamp

The application imports weather data from an external API, stores historical data in a database, and calculates delivery fees using configurable rules.

---

## Features

* Import weather data from Estonian Environment Agency XML API
* Store weather history (no overwriting)
* Calculate delivery fee based on:

   * regional base fee
   * air temperature
   * wind speed
   * weather phenomenon
* Support cities:

   * Tallinn
   * Tartu
   * Pärnu
* Support transport types:

   * car
   * scooter
   * bike
* Return error if transport is forbidden in given weather
* Support optional timestamp for historical fee calculation

---

## Technologies

* Java
* Spring Boot
* Spring Data JPA
* H2 Database
* Liquibase
* MapStruct
* RestClient
* Jackson XML

---

## Architecture

The project follows a layered architecture:

### Controller

Handles REST requests.

### Service

Contains business logic:

* fee calculation
* weather import
* parsing
* transactional saving

### Repository

Accesses database.

### Database

Managed via Liquibase migrations.

---

## REST API

### Endpoint

GET /api/calculateDeliveryFee

### Query Parameters

| Parameter     | Required | Description    |
| ------------- | -------- | -------------- |
| city          | yes      | City name      |
| transportType | yes      | Transport type |
| timestamp     | no       | ISO timestamp  |

### Example Request

GET /api/calculateDeliveryFee?city=Tartu&transportType=bike

### Example Response

{
"deliveryFee": 4.0
}

### Error Responses

#### Forbidden

{
"status": 403,
"error": "Forbidden",
"message": "Usage of selected vehicle type is forbidden"
}

#### Bad Request

{
"status": 400,
"error": "Bad Request",
"message": "Invalid city"
}

---

## Business Logic

### Base Fee

Defined per:

* city
* transport
* timestamp

### Extra Fees

#### Air Temperature

Applied based on temperature ranges.

#### Wind Speed

Applied based on wind speed ranges.

#### Weather Phenomenon

Applied based on weather categories.

---

## Database Design

### Key Tables

#### weather_data

Stores:

* station name
* temperature
* wind speed
* phenomenon
* timestamp

#### station_city_connection

Maps:

* city → weather station

#### transport

Stores available transport types.

#### base_fee

Stores base delivery fees.

#### extra fee tables

Store rules for:

* temperature
* wind
* weather phenomenon


---

## Weather Import

### Source

https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php

### Trigger

* on application startup
* scheduled cron job

### Cron Configuration

weather.import.cron=0 15 * * * *

Runs every hour at minute 15.

---

## Error Handling

Handled via:

* @ResponseStatus
* @RestControllerAdvice

Cases:

* invalid city
* invalid transport
* no data
* forbidden transport
* internal error

---

## Transaction Strategy

Each weather record is saved in a separate transaction:

* prevents full rollback on error
* skips duplicates
* logs failures

---

## Possible Improvements

* Add CRUD for fee rules
* Add integration tests
* Should be created separate tables cities and stations, which hold just names of expected cities and stations.
Station city connection - should connect those two tables.
