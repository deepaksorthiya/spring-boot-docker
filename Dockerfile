FROM openjdk:8-jdk-alpine

FROM maven:3.8.1-openjdk-8-slim

COPY ./pom.xml /pom.xml
COPY ./src /src

RUN mvn clean install

ARG JAR_FILE=/target/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
