#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <profile>"
    exit 1
fi

PROFILE=$1

VERSION_FILE="version.txt"


get_version() {
    if [ -f "$VERSION_FILE" ]; then
        VERSION=$(cat "$VERSION_FILE")
    else
        VERSION=1
        echo "$VERSION" > "$VERSION_FILE"
    fi
}

# Function to increment version
increment_version() {
    VERSION=$((VERSION + 1))
    echo "$VERSION" > "$VERSION_FILE"
}

echo "Building the application (if needed)..."
mvn clean install

# Check if the version needs to be incremented
get_version

# Check if the Docker image already exists for this version
IMAGE_TAG="local:v3.2.$VERSION"
IMAGE_ID=$(docker images -q "$IMAGE_TAG" 2> /dev/null)

if [ -z "$IMAGE_ID" ]; then
    echo "Building the Docker image..."
    docker build -t "$IMAGE_TAG" .
fi

# Stop and remove the existing container (if exists)
CONTAINER_NAME="local3.2.$VERSION"
EXISTING_CONTAINER_ID=$(docker ps -aq -f name="$CONTAINER_NAME")

if [ -n "$EXISTING_CONTAINER_ID" ]; then
    docker stop "$EXISTING_CONTAINER_ID" >/dev/null
    docker rm "$EXISTING_CONTAINER_ID" >/dev/null
fi


echo "Running the application with the '$PROFILE' profile..."



docker run -d -p 8080:8080 --network="host" \
  -e SPRING_PROFILES_ACTIVE="$PROFILE" \
  --name "$CONTAINER_NAME" "$IMAGE_TAG"

DOCKERHUB_USER="dhineshdk07"
DOCKERHUB_REPO="springboot"

docker tag "$IMAGE_TAG" "$DOCKERHUB_USER/$DOCKERHUB_REPO:3.2.$VERSION"
docker push "$DOCKERHUB_USER/$DOCKERHUB_REPO:3.2.$VERSION"
