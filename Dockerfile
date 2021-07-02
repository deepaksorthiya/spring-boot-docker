FROM openjdk:8-jdk-alpine

FROM maven:3.8.1-openjdk-8-slim

COPY ./pom.xml /pom.xml
COPY ./src /src

RUN mvn clean install

ENTRYPOINT ["java","-jar","/target/spring-boot-docker-0.0.1-SNAPSHOT.jar"]
