<h1 style="text-align: center;">Spring Boot Docker Starter Project</h1>

<p style="text-align: center;">
  <a href="https://github.com/deepaksorthiya/spring-boot-docker/actions/workflows/maven-jvm-non-native-build.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/deepaksorthiya/spring-boot-docker/maven-jvm-non-native-build.yml?style=for-the-badge&label=JVM%20Maven%20Build" alt="JVM Maven Build"/>
  </a>  
  <a href="https://github.com/deepaksorthiya/spring-boot-docker/actions/workflows/maven-graalvm-native-build.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/deepaksorthiya/spring-boot-docker/maven-graalvm-native-build.yml?style=for-the-badge&label=GraalVM%20Maven%20Build" alt="GraalVM Maven Build"/>
  </a>
  <a href="https://hub.docker.com/r/deepaksorthiya/spring-boot-docker">
    <img src="https://img.shields.io/docker/pulls/deepaksorthiya/spring-boot-docker?style=for-the-badge" alt="Docker"/>
  </a>
  <a href="https://spring.io/projects/spring-boot">
    <img src="https://img.shields.io/badge/spring--boot-4.0.2-brightgreen?logo=springboot&style=for-the-badge" alt="Spring Boot"/>
  </a>
</p>

# Getting Started

## Requirements:

```
Git: 2.51+
Spring Boot: 4.0.2
Maven: 3.9+
Java: 25
Docker Desktop: Tested on 4.50+
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
java -XX:ArchiveClassesAtExit=application.jsa "-Dspring.context.exit=onRefresh" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
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
java -XX:ArchiveClassesAtExit=application.jsa "-Dspring.context.exit=onRefresh" "-Dspring.aot.enabled=true" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

```bash
java -XX:SharedArchiveFile=application.jsa "-Dspring.aot.enabled=true" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

``
Started Application in 0.92 seconds (process running for 1.066)
``

### 4. JDK 24+ AOT-Cache(Successor of CDS) + Spring-AOT

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
java -XX:AOTMode=record -XX:AOTConfiguration=app.aotconf "-Dspring.aot.enabled=true" "-Dspring.context.exit=onRefresh" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

Create the AOT cache

```bash
java -XX:AOTMode=create -XX:AOTConfiguration=app.aotconf -XX:AOTCache=app.aot "-Dspring.aot.enabled=true" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

Remove ``aotconf`` as its unnecessary now

```bash
rm app.aotconf
```

Run AOT cache enabled

```bash
java -XX:AOTCache=app.aot "-Dspring.aot.enabled=true" -jar spring-boot-docker-0.0.1-SNAPSHOT.jar
```

``
Started Application in 0.817 seconds (process running for 0.988)
``

### 5. Project Leyden

TBD

[YouTube Video](https://www.youtube.com/watch?v=Gb4bFUs1GlY)

### 6. GraalVM Native Mode

* [GraalVM Native Images](https://docs.spring.io/spring-boot/how-to/native-image/developing-your-first-application.html)
* [spring-boot-graalvm-native](https://github.com/deepaksorthiya/spring-boot-graalvm-native.git)

```bash
./mvnw clean -Pnative -DskipTests native:compile
```

```bash
./mvnw clean -Pnative spring-boot:build-image -DskipTests
```

Enable Spring AOT

and add

```xml

<BP_SPRING_AOT_ENABLED>true</BP_SPRING_AOT_ENABLED>
<BP_JVM_CDS_ENABLED>true</BP_JVM_CDS_ENABLED>
```

```xml

<executions>
    <execution>
        <id>process-aot</id>
        <goals>
            <goal>process-aot</goal>
        </goals>
    </execution>
</executions>
```

[Image Optimization](https://www.graalvm.org/latest/reference-manual/native-image/guides/optimize-native-executable-with-pgo/)

```xml

<configuration>
    <buildArgs>
        <buildArg>--pgo</buildArg>
        <buildArg>--gc=G1</buildArg>
        <buildArg>-march=native</buildArg>
    </buildArgs>
</configuration>
```

## Buildpacks

```xml

<env>
    <!-- For native build -->
    <BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
    <!-- For cds-aot build -->
    <BP_SPRING_AOT_ENABLED>true</BP_SPRING_AOT_ENABLED>
    <BP_JVM_CDS_ENABLED>true</BP_JVM_CDS_ENABLED>
</env>

<env>
<BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
<BP_JVM_VERSION>25</BP_JVM_VERSION>
<BP_NATIVE_IMAGE_BUILD_ARGUMENTS>-march=native</BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
</env>

```

### Build Docker Image(docker should be running):

```bash
./mvnw clean spring-boot:build-image -DskipTests
```

* OR for low image size and multi-stage build
  (Run from workspace where Dockerfile is located)

```bash
docker build --progress=plain --no-cache -f <dockerfile> -t deepaksorthiya/spring-boot-docker .
```

* OR Build Using Local Fat Jar In Path ``target/spring-boot-docker-0.0.1-SNAPSHOT.jar``

```bash
docker build --build-arg JAR_FILE=target/spring-boot-docker-0.0.1-SNAPSHOT.jar -f Dockerfile.jvm --no-cache --progress=plain -t deepaksorthiya/spring-boot-docker .
```

* OR if above not work try below command

***you should be in jar file path to work build args***

```bash
cd target
docker build --build-arg JAR_FILE=spring-boot-docker-0.0.1-SNAPSHOT.jar -f ./../Dockerfile.jvm --no-cache --progress=plain -t deepaksorthiya/spring-boot-docker .
```

* Run In Docker Container

```bash
docker run -p 8080:8080 --name spring-boot-docker -it deepaksorthiya/spring-boot-docker
```

| Dockerfile Name                                            |                          Description                           |
|------------------------------------------------------------|:--------------------------------------------------------------:|    
| [Dockerfile](Dockerfile)                                   |  multi stage docker file with Spring AOT and JDK24+ AOT Cache  |
| [Dockerfile.jlink](Dockerfile.jlink)                       |      single stage using JDK jlink feature to reduce size       |
| [Dockerfile.jvm](Dockerfile.jvm)                           |    single stage using with Spring AOT and JDK24+ AOT Cache     |
| [Dockerfile.native](Dockerfile.native)                     |  single stage using graalvm native image using oraclelinux 9   |
| [Dockerfile.native-distro](Dockerfile.native-distro)       | single stage using graalvm native image distroless linux image |
| [Dockerfile.native-micro](Dockerfile.native-micro)         |   single stage using graalvm native image micro linux image    |
| [Dockerfile.native-multi](Dockerfile.native-multi)         |    multi stage using graalvm native image micro linux image    |
| [Dockerfile.springlayeredjar](Dockerfile.springlayeredjar) |          multi stage using spring layererd layout jar          |
| [Dockerfile.springlayoutjar](Dockerfile.springlayoutjar)   |              multi stage using spring layout jar               |

**_Note: In [Dockerfile.jlink](Dockerfile.jlink) check ``/optimized-jdk-25``. This will be created under OS root path
while only``optimized-jdk-25`` (without slash) created path
specified in ``WORKDIR``, Which is in this case ``/workspace/app``_**

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

