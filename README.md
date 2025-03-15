[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/vlo9idtn)
# lab1-wa2025

# RouteGenerator
## Run
```
dcoker compose up -d --build
```

# RouteAnalyzer
## Run

To run the docker container
```
docker compose up -d --build
```

If you want to run without compose
```
docker build -t <image_name> .
docker run -v <path_to_evaluation>:/app/evaluation -d \
    --name <container_name> <image_name> \
    java -jar /app/app.jar
```