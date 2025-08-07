<h1 style="text-align: center;">Spring Boot Docker Starter Project</h1>

<p style="text-align: center;">
  <a href="https://github.com/deepaksorthiya/spring-boot-docker/actions/workflows/maven-jvm-non-native-build.yml">
    <img src="https://github.com/deepaksorthiya/spring-boot-docker/actions/workflows/maven-jvm-non-native-build.yml/badge.svg" alt="JVM Maven Build"/>
  </a>  
<a href="https://github.com/deepaksorthiya/spring-boot-docker/actions/workflows/maven-graalvm-native-build.yml">
    <img src="https://github.com/deepaksorthiya/spring-boot-docker/actions/workflows/maven-graalvm-native-build.yml/badge.svg" alt="GraalVM Maven Build"/>
  </a>
  <a href="https://hub.docker.com/r/deepaksorthiya/spring-boot-docker">
    <img src="https://img.shields.io/docker/pulls/deepaksorthiya/spring-boot-docker" alt="Docker"/>
  </a>
  <a href="https://spring.io/projects/spring-boot">
    <img src="https://img.shields.io/badge/spring--boot-3.5.4-brightgreen?logo=springboot" alt="Spring Boot"/>
  </a>
</p>

# Getting Started

## Requirements:

```
Git: 2.49.0
Spring Boot: 3.5.4
Maven: 3.9+
Java: 24
Docker Desktop: Tested on 4.42.0
```

### Clone this repository:

```bash
git clone https://github.com/deepaksorthiya/spring-boot-docker.git
```

```bash
cd spring-boot-docker
```

## Running Application In Different Mode and Startup Performance

### 1. Exploded Jar

Build Project

```bash
./mvnw clean package -DskipTests
```

Remove directory ``application`` if exists

```bash
rm -f -R application
```

Create Self Exploded Jar file

```bash
java -Djarmode=tools -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar extract --destination application
```

Run Self Exploded Jar file

```bash
java -jar .\application\spring-boot-docker-0.0.1-SNAPSHOT.jar
```

``
Started Application in 2.309 seconds (process running for 2.54)
``

### 2. CDS Mode With Exploded Jar

```bash
./mvnw clean package -DskipTests
```

```bash
rm -f -R application
```

```bash
java -Djarmode=tools -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar extract --destination application
```

```bash
cd application
```

Training Run

```bash
java -XX:ArchiveClassesAtExit=application.jsa -D"spring.context.exit=onRefresh" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

Production Run

```bash
java -XX:SharedArchiveFile=application.jsa -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

``
Started Application in 1.338 seconds (process running for 1.487)
``

### 3. Spring-AOT + CDS

Build Project With AOT mode

```bash
./mvnw -Pnative -DskipTests clean package
```

```bash
rm -f -R application
```

```bash
java -Djarmode=tools -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar extract --destination application
```

```bash
cd application
```

```bash
java -XX:ArchiveClassesAtExit=application.jsa -D"spring.context.exit=onRefresh" -D"spring.aot.enabled=true" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

```bash
java -XX:SharedArchiveFile=application.jsa -D"spring.aot.enabled=true" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

``
Started Application in 0.92 seconds (process running for 1.066)
``

### 4. JDK 24 AOT-Cache(Successor of CDS) + Spring-AOT

```bash
./mvnw -Pnative -DskipTests clean package
```

```bash
java -Djarmode=tools -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar extract --destination application
```

```bash
cd application
```

Execute the AOT cache training run

```bash
java -XX:AOTMode=record -XX:AOTConfiguration=app.aotconf -D"spring.aot.enabled=true" -D"spring.context.exit=onRefresh" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

Create the AOT cache

```bash
java -XX:AOTMode=create -XX:AOTConfiguration=app.aotconf -XX:AOTCache=app.aot -D"spring.aot.enabled=true" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

Remove ``aotconf`` as its unnecessary now

```bash
rm app.aotconf
```

Run AOT cache enabled

```bash
java -XX:AOTCache=app.aot -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

``
Started Application in 0.817 seconds (process running for 0.988)
``

### 5. Project Leyden

TBD

[YouTube Video](https://www.youtube.com/watch?v=Gb4bFUs1GlY)

### 6. GraalVM Native Mode

[GraalVM Native Images](https://docs.spring.io/spring-boot/how-to/native-image/developing-your-first-application.html)

## Buildpacks

```xml

<env>
    <!-- For native build -->
    <BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
    <!-- For cds-aot build -->
    <BP_SPRING_AOT_ENABLED>true</BP_SPRING_AOT_ENABLED>
    <BPL_SPRING_AOT_ENABLED>true</BPL_SPRING_AOT_ENABLED>
    <BP_JVM_CDS_ENABLED>true</BP_JVM_CDS_ENABLED>
</env>

<env>
    <BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
    <BP_JVM_VERSION>24</BP_JVM_VERSION>
</env>

```

### Build Docker Image(docker should be running):

```bash
./mvnw clean spring-boot:build-image -DskipTests
```

OR for low image size

```bash
docker build --progress=plain -t deepaksorthiya/spring-boot-docker .
```

### Rest APIs

http://localhost:8080/ <br>
http://localhost:8080/server-info <br>
http://localhost:8080/rest-client <br>
http://localhost:8080/rest-client-error <br>
http://localhost:8080/rest-server-error <br>
http://localhost:8080/rest-client-delay

### Reference Documentation

For further reference, please consider the following sections:

* [Class Data Sharing](https://docs.spring.io/spring-boot/reference/packaging/class-data-sharing.html)
* [Dockerfiles](https://docs.spring.io/spring-boot/reference/packaging/container-images/dockerfiles.html)
* [CDS with Spring Boot](https://bell-sw.com/blog/how-to-use-cds-with-spring-boot-applications/)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/maven-plugin/build-image.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/reference/actuator/index.html)
* [Spring Web](https://docs.spring.io/spring-boot/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot//io/validation.html)
* [Flyway Migration](https://docs.spring.io/spring-boot/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)

