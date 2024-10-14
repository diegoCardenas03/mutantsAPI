# Detector de mutantes

Este proyecto consiste en una aplicación backend construida con Spring Boot, diseñada para evaluar si un individuo es mutante según su secuencia de ADN. La aplicación recibe la secuencia en un formato determinado y, mediante una serie de validaciones, detecta la presencia de patrones característicos de mutaciones.

## Tecnologías Utilizadas
Java 17

Spring Boot

Gradle

JUnit

JPA

Docker

Postman

## Estructura del proyecto

Controladores: Exponen los endpoints de la API REST.

Servicios: Contienen la lógica de negocio, como la detección de mutantes.

Repositorio: Interactúa con la base de datos utilizando JPA.

## Instalación

1. Clona el repositorio:
    ```sh
    git clone https://github.com/diegoCardenas03/mutantsAPI.git
    cd mutantsAPI
    ```

## Modo de Uso

- POST /mutant: Verifica si un ADN pertenece a un mutante.

  - URL: https://mutantsapi.onrender.com/mutant

- GET /stats: Retorna las estadísticas de mutantes y humanos.

  - URL: https://mutantsapi.onrender.com/stats



