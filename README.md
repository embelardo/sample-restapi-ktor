# sample-restapi-ktor
A sample REST API in Kotlin using Ktor.

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

