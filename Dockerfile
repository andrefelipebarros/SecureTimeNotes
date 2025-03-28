FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY ./securetimenotes /app

RUN apt-get install maven -y

WORKDIR /app

RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8081

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]