# Energy Community Monitoring System

## Overview

This project was developed as part of the **Distributed Systems**
course. It simulates an energy community in which households both
produce and consume electrical energy. The system follows a microservice
architecture and demonstrates asynchronous communication using RabbitMQ,
persistence using PostgreSQL, and a JavaFX desktop application for
visualization.

The project consists of several independent Spring Boot services that
communicate through message queues and REST APIs.

------------------------------------------------------------------------

## Features

-   Simulation of photovoltaic energy production
-   Simulation of household energy consumption
-   Asynchronous communication using RabbitMQ
-   Hourly aggregation of energy production and consumption
-   Calculation of community energy usage and grid dependency
-   PostgreSQL persistence using Spring Data JPA
-   Database migrations with Flyway
-   REST API for GUI communication
-   JavaFX desktop application with live monitoring
-   Docker Compose setup for PostgreSQL and RabbitMQ

------------------------------------------------------------------------

## System Architecture

flowchart TB
    A[JavaFX GUI]
    B[Spring Boot REST API\n<small>Port 8083</small>]
    C[(PostgreSQL\n<small>Port 5432</small>)]
    D[Current Percentage Service\n<small>Port 8080</small>]
    E[Usage Service\n<small>Port 8084</small>]
    F[(RabbitMQ\n<small>Port 5672</small>)]
    G[Energy Producer\n<small>Port 8081</small>]
    H[Energy User\n<small>Port 8082</small>]
    I[Weather API\n<small>open-meteo</small>]
    J[Time of Day]


%% Frontend
    A --- |↓ GET /energy/current| B
    A --- |↓ GET /energy/historical?start=...&end=...| B

%% API to DB
    B --- |↓ read tables| C

%% DB to services
    C --- |↑ upsert currentpercentage table| D
    C --- |↑ upsert energyusage table| E

%% Services to / from RabbitMQ
    D ---|"↑ update message\n(queue: current_percentage)"| F
    E ---|"↑ producer/user message\n(queue: energy_message)"| F
    E ---|"↓ update message\n(queue: current_percentage)"| F

%% to Producer/User to RabbitMQ
    F --- |"↑ producer message\n(queue: energy_message)"| G
    F --- |"↑ user message\n(queue: energy_message)"| H

%% External data sources
    G --- |↓ use| I

------------------------------------------------------------------------

## Components

### EnergyProducer

-   Simulates photovoltaic energy production.
-   Retrieves current weather conditions from the Open-Meteo API.
-   Publishes production messages to RabbitMQ.

### EnergyUser

-   Simulates household electricity consumption.
-   Publishes consumption messages to RabbitMQ.

### UsageService

-   Aggregates hourly energy values.
-   Calculates community and grid usage.
-   Stores data in PostgreSQL.
-   Publishes percentage messages.

### CurrentPercentageService

-   Calculates community depletion and grid portion.
-   Stores calculated percentages.

### JavaFxGuiAPI

Provides REST endpoints for the JavaFX GUI.

### JavaFX GUI

Displays current and historical energy statistics.

------------------------------------------------------------------------

## Technologies

  Technology        Purpose
  ----------------- ----------------------
  Java 21           Programming language
  Spring Boot       Backend framework
  Spring Data JPA   Database access
  Hibernate         ORM
  RabbitMQ          Messaging
  PostgreSQL        Database
  Flyway            Database migrations
  JavaFX            Desktop GUI
  Jackson           JSON mapping
  Docker Compose    Infrastructure

------------------------------------------------------------------------

## Running the Project

### Prerequisites

-   Java 21
-   Maven
-   Docker Desktop

### Start infrastructure

``` bash
docker compose up -d
```

### Start applications

1.  UsageService
2.  CurrentPercentageService
3.  EnergyProducer
4.  EnergyUser
5.  JavaFxGuiAPI

Finally, start the JavaFX GUI.

------------------------------------------------------------------------

## Lessons Learned

This project provided practical experience with microservice
architectures, asynchronous communication using RabbitMQ, Spring Boot,
Spring Data JPA, Hibernate, Flyway, Docker, JavaFX, REST APIs and JSON
serialization using Jackson.

------------------------------------------------------------------------

## Authors

Developed as part of the Distributed Systems course.

The Git history documents the development process and individual
contributions.
