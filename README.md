# dltavro

Load and Transformation of data with the help of Apache Avro

## Build Setup

1. Check out this repository.

2. Add ".env" file and set environmental variables in it.

3. Install Docker.

## Environment variables

Below is the list of recommended content for your local .env file.

BE_SERVER_PORT=9091
BUILD_PROFILE=development

## Docker

### Start the system

```bash
docker compose up -d
```

### Shutdown the system

```bash
docker compose down
```

### Rebuild an individual service

```bash
docker compose build backend
```

### Check the latest build date of a service

```bash
docker inspect -f '{{.Created}}' backend
```

### Redeploy an individual service

```bash
docker compose up --no-deps -d backend
```

### Connect to logs of Spring Boot backend

```bash
docker logs --tail 50 --follow --timestamps backend
```

### push to Docker Hub

```bash
docker tag spg-backend iiplabs/dltavro-backend:0.0.1
docker push iiplabs/dltavro-backend:0.0.1
```
