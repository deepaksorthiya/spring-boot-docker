# Getting Started

## Requirements:

```
Spring Boot: 3.3.5
Maven: 3.9+
Java: 17
Docker Engine: 25.0.2
Minikube: v1.32.0
```

### Clone this repository:

```bash
git clone https://github.com/deepaksorthiya/spring-boot-3-docker.git
```

```bash
cd spring-boot-3-docker
```

### Build Docker Image(docker should be running):

```bash
./mvnw clean spring-boot:build-image -DskipTests
```

### Rest APIs

http://localhost:8080/ <br>
http://localhost:8080/api/headers

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

