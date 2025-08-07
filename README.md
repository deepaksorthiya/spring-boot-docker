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
    <img src="https://img.shields.io/badge/spring--boot-3.5.0-brightgreen?logo=springboot" alt="Spring Boot"/>
  </a>
</p>

# Getting Started

## Requirements:

```
Git: 2.49.0
Spring Boot: 3.5.0
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

### Build Docker Image(docker should be running):

```bash
./mvnw clean spring-boot:build-image -DskipTests
```

OR

```bash
docker build --progress=plain -t deepaksorthiya/spring-boot-docker .
```

### CDS Mode

```bash
./mvnw clean package -DskipTests
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

### AOT Mode

```bash
./mvnw clean package -DskipTests
```

```bash
java -Djarmode=tools -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar extract --destination application
```

```bash
cd application
```

```bash
java -XX:AOTMode=record -XX:AOTConfiguration=app.aotconf -D"spring.context.exit=onRefresh" -jar .\spring-boot-docker-0.0.1-SNAPSHOT.jar
```

```bash
java -XX:AOTMode=create -XX:AOTConfiguration=app.aotconf -XX:AOTCache=app.aot -jar .\spring-boot-docker-0.0.1-SNAPSHOT.jar
```

```bash
java -XX:AOTCache=app.aot -jar .\spring-boot-docker-0.0.1-SNAPSHOT.jar
```

```xml

<env>
    <!-- For native build -->
    <BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
    <!-- For cds-aot build -->
    <BP_SPRING_AOT_ENABLED>true</BP_SPRING_AOT_ENABLED>
    <BPL_SPRING_AOT_ENABLED>true</BPL_SPRING_AOT_ENABLED>
    <BP_JVM_CDS_ENABLED>true</BP_JVM_CDS_ENABLED>
</env>
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

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/maven-plugin/build-image.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/reference/actuator/index.html)
* [Spring Web](https://docs.spring.io/spring-boot/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot//io/validation.html)
* [Flyway Migration](https://docs.spring.io/spring-boot/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

