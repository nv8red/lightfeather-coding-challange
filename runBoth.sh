#!/bin/bash
docker kill lf-frontend
docker rm lf-frontend
docker image rm lf-frontend-image
docker build -t lf-frontend-image ./lightfeather_frontend
docker kill spring-template
docker rm spring-template
docker image rm spring-temp-image
docker build -t spring-temp-image ./spring-template
docker run -itd -p 8080:8080 --name=spring-template spring-temp-image
docker run -itd -p 5173:5173 --name=lf-frontend lf-frontend-image
echo "Visit http://localhost:5173/ to see the app!"
