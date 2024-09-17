
# Global Application Suite

## Overview

This project is a scheduling application for a Hotel, designed to manage hotel reservations with localization and currency support. The application consists of a back-end built using Spring Boot and a front-end implemented with Angular. It provides features such as multi-language support, currency display, and time zone conversion.

## Key Features

### 1. **Localization and Internationalization**
- The application displays welcome messages in both English and French, based on the user's locale.
- Two resource bundles (`welcome_en_US` for English and `welcome_fr_CA` for French) are used to store the welcome messages.
- A REST API endpoint `/welcome` is provided to fetch localized welcome messages in a multi-threaded manner, displaying both languages concurrently.

### 2. **Currency Display**
- The room prices are displayed in multiple currencies: U.S. dollars (USD), Canadian dollars (CAD), and euros (EUR).
- The front-end is designed to show each currency on a separate line, providing clarity to the user.

### 3. **Time Zone Conversion**
- The application displays the scheduled time for an online presentation in three different time zones: Eastern Time (ET), Mountain Time (MT), and Coordinated Universal Time (UTC).
- A Java method is implemented to convert the presentation time between these time zones and display them on the front-end.

### 4. **Dockerization**
- The application is fully Dockerized, allowing it to run in a containerized environment.
- A `Dockerfile` is provided to build the application image, which includes both the Spring Boot back-end and Angular front-end.
- Instructions are included for deploying the application to cloud services using Docker.

### 5. **Cloud Deployment**
- The project includes a guide on how to deploy the application on a cloud platform, with a specific focus on Azure deployment.

## Technologies Used
- **Back-End**: Java, Spring Boot
- **Front-End**: Angular
- **Localization**: Java Resource Bundles
- **Time Zone Management**: Java Time API
- **Containerization**: Docker
- **Build Management**: Maven

## How to Run the Application
1. **Clone the Repository**: Clone the project into your local environment.
2. **Build the Application**: Use Maven to build the back-end and Angular CLI for the front-end.
3. **Run the Application**:
    - You can run the application locally or build and run the Docker image.
4. **Access the Application**: Once the application is running, you can access it via `http://localhost:8080`.

## Docker Usage
1. **Build Docker Image**:
    ```bash
    docker build -t landon-hotel-app .
    ```
2. **Run Docker Container**:
    ```bash
    docker run -p 8080:8080 landon-hotel-app
    ```

## Cloud Deployment
- A guide for deploying the application on cloud services like Azure is provided in the `/deploying-on-cloud` folder.
