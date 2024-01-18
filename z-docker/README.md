
docker-compose -f docker-compose-oracle.yml up

TEMPORAL
git clone https://github.com/temporalio/docker-compose.git
cd docker-compose
docker compose up

ARG IMAGE_REPO
FROM --platform=linux/amd64 $IMAGE_REPO/bellsoft/liberica-openjdk-alpine:19
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE /app/app.jar
ARG ENV
ENTRYPOINT exec java -Duser.timezone=Asia/Ho_Chi_Minh -jar -Dlogging.config=/config/log/logback-spring.xml /app/app.jar
