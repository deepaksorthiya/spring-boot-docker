FROM openjdk:8-jdk-alpine

ARG JAR_FILE='target/*.jar'

COPY ${JAR_FILE} app.jar
# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]
