#!/bin/bash

. ./settings.sh
export DOCKER_CONTAINER_NAME=$DOCKER_CONTAINER_NAME

{
  echo "export DOCKER_CONTAINER_NAME=$DOCKER_CONTAINER_NAME";
  echo "export DATASOURCES_DEFAULT_URL=$DATASOURCES_DEFAULT_URL";
  echo "export DATASOURCES_DEFAULT_USERNAME=$DATASOURCES_DEFAULT_USERNAME";
  echo "export DATASOURCES_DEFAULT_PASSWORD=$DATASOURCES_DEFAULT_PASSWORD";
} >> resources.sh

echo "docker run -d \
          --name=$DOCKER_CONTAINER_NAME \
          -p 3306:3306 \
          -e MYSQL_DATABASE=db \
          -e MYSQL_USER=sherlock \
          -e MYSQL_PASSWORD=elementary \
          -e MYSQL_ALLOW_EMPTY_PASSWORD=true \
          mysql:latest"

#tag::run-mysql-container[]
docker run -d \
    --name=$DOCKER_CONTAINER_NAME \
    -p 3306:3306 \
    -e MYSQL_DATABASE=db \
    -e MYSQL_USER=$DATASOURCES_DEFAULT_USERNAME \
    -e MYSQL_PASSWORD=$DATASOURCES_DEFAULT_PASSWORD \
    -e MYSQL_ALLOW_EMPTY_PASSWORD=true \
     mysql:latest
#end::run-mysql-container[]
sleep 30

echo 'Resources created!'