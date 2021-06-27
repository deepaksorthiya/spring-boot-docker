FROM openjdk:8-jdk-alpine

FROM maven:3.8.1-openjdk-8-slim

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

WORKDIR /spring-boot-docker

COPY ./pom.xml /spring-boot-docker/pom.xml
COPY ./src /spring-boot-docker/src

RUN mvn clean install

ARG JAR_FILE=/target/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
