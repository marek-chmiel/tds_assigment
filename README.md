# TDS assigment
Java assigment for TDS

# Getting Started

ParkingLot is a Spring Boot application with H2 in-memory database which allows to check status of parking spaces, park vehicles and bill them.
Application is initialized with 10 free parking spaces.

## Requirements
Project was implemented using the following requirements:
* Java 25
* Maven 3.9.16

## Start application
1. Navigate in terminal to REPOSITORY_HOME
2. cd ParkingLot/
3. Start application:
    - Unix / macOS: ./mvnw spring-boot:run
    - Windows: mvnw.cmd spring-boot:run
    - if Maven is installed globally: mvn spring-boot:run

## Run tests
mvn clean install test

## Setup
- Application is configured to use H2 in-memory database, it is cleaned during every restart of application. It is possible to connect to that db via tcp on port 9092 - JDBC URL: jdbc:h2:tcp://localhost:9092/mem:parkinglotdb
- Initially 10 parking spaces are created. It can be changed by modifying liquibase script: 'src/main/resources/db/changelog/data/task1/001-data.parking_space.changelog.xml'

## Assumptions
- Bill value is required to be of 'double' type however it is not the best approach for price calculations due to floating-point inaccuracies. Internally value is calculated using BigDecimal type
- It was not explicitly required but parking operations handles concurrent requests
- I assume that all vehicles take only one parking space no matter of vehicle type
- I tried to use appropriate HTTP status for different use cases and error message
- I assume there are no limits for calculating bill value (like maximum daily limit)
- I assume that additional charge for bill is added after every 5 full minutes
