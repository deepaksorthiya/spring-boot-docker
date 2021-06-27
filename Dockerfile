FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-javaagent:/sharedFiles/AppServerAgent/javaagent.jar","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
