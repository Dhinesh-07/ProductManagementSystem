#!/bin/bash


if [ -z "$1" ]; then
    echo "Usage: $0 <profile>"
    exit 1
fi

PROFILE=$1


if [ "$PROFILE" = "dev" ]; then

  mvn clean install -Pdev
  mvn spring-boot:run -Pdev

elif [ "$PROFILE" = "prod" ]; then

    mvn clean install -Pprod
    mvn spring-boot:run -Pprod
else
    echo "Invalid profile. Use 'dev' or 'prod'."
    exit 1
fi