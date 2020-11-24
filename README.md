[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/fxrobin/api-impl-demo)
![Java CI with Maven](https://github.com/fxrobin/api-impl-demo/workflows/Java%20CI%20with%20Maven/badge.svg)
![Maven Package](https://github.com/fxrobin/api-impl-demo/workflows/Maven%20Package/badge.svg)


# api-impl-demo

A simple REST API following REST best practices.

This project uses Quarkus.

## How to run

### Dev mode

```
$ ./mvnw quarkus:dev
```

Then open a browser `http://localhost:8080/openapi-ui`

### Production mode UBER-JAR

Generating the uber-jar:

```
$ /§mvnw package -Dquarkus.package.type=uber-jar
```

then run it:

```
$ java -jar ./target/api-impl-demo-[version]-SNAPSHOT-runner.jar
```

then browse to: 

- OpenApi definition : `http://localhost:8080/openapi`
- API : `http://localhost:8080/api/v1/video-games?page=0&size=100`
- Health: `http://localhost:8080/health`
- Metrics: `http://localhost:8080/metrics`

## Examples

Request `GET /api/v1/video-games?page=5&size=10`

Response (JSON)
```json
[
   {
      "id":"0ee652a8-0222-4da7-9623-f35b57a7a914",
      "name":"AIRLINE ST",
      "genre":"simulation",
      "version":1
   },
   {
      "id":"01e495ad-cb28-472f-94a6-3170395cc9b4",
      "name":"AIRLINE ST II",
      "genre":"simulation",
      "version":1
   },
   {
      "id":"07638287-8f45-4be8-a887-c57f350a9ce7",
      "name":"ALBEDO",
      "genre":"shoot-them-up",
      "version":1
   },
   {
      "id":"46368d7e-c439-4da8-82fb-f2f8afb09ddc",
      "name":"ALBION",
      "genre":"rpg",
      "version":1
   },
   {
      "id":"0a8e24b3-7e37-4532-8de4-b82b6ae441a2",
      "name":"ALCANTOR",
      "genre":"arcade",
      "version":1
   },
   {
      "id":"6c8b0256-c771-4a03-8324-70ce6674933f",
      "name":"ALCATRAZ",
      "genre":"simulation",
      "version":1
   },
   {
      "id":"56417168-1e57-4197-a490-56258e5405eb",
      "name":"ALCON",
      "genre":"shoot-them-up",
      "version":1
   },
   {
      "id":"772a7578-b276-487d-8d61-e2ab824da2b3",
      "name":"ALF : THE FIRST ADVENTURE",
      "genre":"reflexion",
      "version":1
   },
   {
      "id":"d5bd6aaa-674d-4e7f-ae8e-53118de897c6",
      "name":"ALIEN BLAST",
      "genre":"shoot-them-up",
      "version":1
   },
   {
      "id":"2589d502-4619-4728-b688-9cece2a8a3ab",
      "name":"ALIEN BUSTERS IV",
      "genre":"shoot-them-up",
      "version":1
   }
]
```
