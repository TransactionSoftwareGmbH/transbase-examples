# Transbase Spring Boot JPA

### Requirements

You need a running transbase database for this example (//localhost:2024/cashbook)
with user `tbadmin` and password `transbase`.
Transbase can be started with the included docker-compose script:

`docker-compose up`

then create a new cashbook database with

- `docker exec -it transbase tbi`
- `connect //localhost:2024/admin tbadmin transbase`
- `create database cashbook set encryption=none;`

Start the application with

`./gradlew bootRun` or run `Application.java` from your IDE.

A cashbook rest api is available at
```http://localhost:8080/cashbooks```

### Reference Documentation

For further reference, please consider the following sections:

- [Transbase Getting Started](https://www.transaction.de/fileadmin/public/transbase/8.4/docu/getstart.xhtml)
- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
