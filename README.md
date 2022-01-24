# sample-restapi-ktor
A sample REST API in Kotlin using Ktor.

Libraries used:
- Ktor for REST API web server
- Koin for dependency injection
- Jackson for JSON content negotiation
- For unit tests:
  - JUnit 5
  - Spek for writing tests in specification style
- MockK for mocking

## Prerequisites

The following should be available on your workstation:
- curl
- IntelliJ
- make

## Steps to run this REST API

- Clone this repo.
- Import the project into IntelliJ.
- Go to `Application.kt` under `serums-restapi` and click the green arrow beside the `main` function.
- To list the available REST API calls, run the `make` command from the root directory.
    ```
    $ make
    delete.api.serums.0  Delete serum with ID 0 (Serum Worms Sackrider)
    get.api.serums.0     Read serum with ID 0
    get.api.serums.100   Read non-existent serum
    get.api.serums       Read all serums
    post.api.serums      Create new serum (Serum Ovaltine Splern)
    put.api.serums.1     Update serum with ID 1 (Serum Squids Overturf)
    ```

- To send a request, follow the example below:
    ```
    $ make get.api.serums
    curl -i -X GET http://localhost:8081/api/serums
    HTTP/1.1 200 OK
    Content-Type: application/json; charset=UTF-8
    transfer-encoding: chunked
    
    [ {
    "id" : 0,
    "name" : "Serum Worms Sackrider",
    "phase" : "ALPHA",
    "tester" : "Felice Mienn",
    "potency" : 0.78,
    "testDate" : "2022-01-20"
    }, {
    "id" : 1,
    "name" : "Serum Squids Overturf",
    "phase" : "BETA",
    "tester" : "Praeda Rakespear",
    "potency" : 0.87,
    "testDate" : "2022-01-20"
    } ]
    ```

- If you delete all the data and/or want to reset, simply restart the application.

## Project Description

### Overview

The Serum REST API has a single kind of resource called a *serum*.

The following operations can be performed in regard to this resource:
- Create - A new serum can be created.
- Read - All serums or a specific serum can be retrieved.
- Update - An existing serum can be updated.
- Delete - An existing serum can be deleted.

### Project Structure

The Serum REST API is structured as a Gradle multi-project build consisting of one root project and three subprojects.

    .
    ├── serums-restapi      - REST API service code
    ├── serums-service-data - Data access service code
    └── serums-shared       - Common code like Serum type that represents serum in application

Dependency injection is used to provide a reference to the data access service to the REST API service.

There is no real database. The data is represented in-memory by a simple list that contains Serum objects. And all that is encapsulated by the data access service. In the real world, the data access service would go to some kind of data repository.

Two sets of unit tests are available. One set written in JUnit 5 with no mocking. And another set written in Spek following the specification style of testing and using a mock data access service.

As a server application, Ktor officially supports the following server engines. Netty is used in this project.
- Netty
- Jetty
- Tomcat
- CIO (Coroutine-based I/O)

### What's Missing

The Serum REST API has no security, nor sessions for state.
