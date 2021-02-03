[![Codacy Badge](https://api.codacy.com/project/badge/Grade/886391c8c9464ba7828752cf2bbab230)](https://app.codacy.com/gh/fxrobin/api-impl-demo?utm_source=github.com&utm_medium=referral&utm_content=fxrobin/api-impl-demo&utm_campaign=Badge_Grade)
[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/fxrobin/api-impl-demo)
![Java CI with Maven](https://github.com/fxrobin/api-impl-demo/workflows/Java%20CI%20with%20Maven/badge.svg)
![Maven Package](https://github.com/fxrobin/api-impl-demo/workflows/Maven%20Package/badge.svg)
[![CodeFactor](https://www.codefactor.io/repository/github/fxrobin/api-impl-demo/badge)](https://www.codefactor.io/repository/github/fxrobin/api-impl-demo)

# api-impl-demo

A simple REST API following REST best practices.

This project uses Quarkus.

Note: this project can be build in native mode with graal-vm, but it cannot be executed due to embedded H2.
H2 cannot be executed in native mode up to now. (not supported feature by Quarkus).

## How to run

### Dev mode

```bash
$ ./mvnw quarkus:dev
```

Then open a browser `http://localhost:8080/openapi-ui`

### Production mode UBER-JAR

Generating the uber-jar:

```bash
$ ./mvnw package -Dquarkus.package.type=uber-jar
```

then run it:

```bash
$ java -jar ./target/api-impl-demo-[version]-SNAPSHOT-runner.jar
```

then browse to: 

- OpenApi definition : `http://localhost:8080/v1/openapi`
- OpenApi UI : `http://localhost:8080/openapi-ui`
- API : `http://localhost:8080/v1/api/video-games?page=0&size=100`
- Health: `http://localhost:8080/v1/health`
- Health UI: `http://localhost:8080/v1/health-ui`
- Metrics: `http://localhost:8080/v1/metrics`

## Examples

### HEAD `/v1/api/video-games`

```text
HTTP/1.1 204 No Content
Resource-Count: 1619
connection: keep-alive
```


### GET `/v1/api/video-games?page=5&size=10`


```json
{
  "metadata": {
    "currentPage": 5,
    "pageCount": 162,
    "resourceCount": 1619
  },
  "data": [
    {
      "id": "0ee652a8-0222-4da7-9623-f35b57a7a914",
      "name": "AIRLINE ST",
      "genre": "simulation",
      "version": 0
    },
    {
      "id": "01e495ad-cb28-472f-94a6-3170395cc9b4",
      "name": "AIRLINE ST II",
      "genre": "simulation",
      "version": 0
    },
    {
      "id": "07638287-8f45-4be8-a887-c57f350a9ce7",
      "name": "ALBEDO",
      "genre": "shoot-them-up",
      "version": 0
    },
    {
      "id": "46368d7e-c439-4da8-82fb-f2f8afb09ddc",
      "name": "ALBION",
      "genre": "rpg",
      "version": 0
    },
    {
      "id": "0a8e24b3-7e37-4532-8de4-b82b6ae441a2",
      "name": "ALCANTOR",
      "genre": "arcade",
      "version": 0
    },
    {
      "id": "6c8b0256-c771-4a03-8324-70ce6674933f",
      "name": "ALCATRAZ",
      "genre": "simulation",
      "version": 0
    },
    {
      "id": "56417168-1e57-4197-a490-56258e5405eb",
      "name": "ALCON",
      "genre": "shoot-them-up",
      "version": 0
    },
    {
      "id": "772a7578-b276-487d-8d61-e2ab824da2b3",
      "name": "ALF : THE FIRST ADVENTURE",
      "genre": "reflexion",
      "version": 0
    },
    {
      "id": "d5bd6aaa-674d-4e7f-ae8e-53118de897c6",
      "name": "ALIEN BLAST",
      "genre": "shoot-them-up",
      "version": 0
    },
    {
      "id": "2589d502-4619-4728-b688-9cece2a8a3ab",
      "name": "ALIEN BUSTERS IV",
      "genre": "shoot-them-up",
      "version": 0
    }
  ]
}
```

### GET `/v1/api/video-games/098d7670-ac32-49e7-9752-93fb1d16d495`

```json
{
  "id": "098d7670-ac32-49e7-9752-93fb1d16d495",
  "name": "BIONIC COMMANDO",
  "genre": "shoot-them-up",
  "version": 0
}
```

### GET `/v1/api/video-games?page=5&size=10&name=like:xeno`

```json
{
  "metadata": {
    "currentPage": 0,
    "pageCount": 1,
    "resourceCount": 3
  },
  "data": [
    {
      "id": "8642b67f-140f-4feb-a860-e391a11cdae5",
      "name": "XENOMORPH",
      "genre": "rpg",
      "version": 0
    },
    {
      "id": "c7b81d89-8da4-4abe-baed-03098861ed26",
      "name": "XENON",
      "genre": "shoot-them-up",
      "version": 0
    },
    {
      "id": "b557289b-1b66-4a1b-9804-c77daecbad5a",
      "name": "XENON 2 : MEGABLAST",
      "genre": "shoot-them-up",
      "version": 0
    }
  ]
}
```