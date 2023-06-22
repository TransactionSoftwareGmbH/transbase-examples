# Transbase Java JDBC

### Requirements

You need a running transbase database for this example (//localhost:2024/cashbook)
with user `tbadmin` and password `transbase`.
Transbase can be started with the included [docker-compose.yml](../docker-compose.yml) script:

`docker-compose up`

then create a new cashbook database with

- `docker exec -it transbase tbi`
- `connect //localhost:2024/admin tbadmin transbase`
- `create database cashbook set encryption=none;`

In your IDE e.g. IntelliJ simply run the main application `Run Main.java`

or from the command line compile the project with

```javac -d out Cashbook.java CashbookService.java Main.java```

then run:

```java -cp out:lib/tbbjdbc.jar Main``` (unix based)

```java -cp "out;lib/tbjdbc.jar" Main``` (windows)


### Reference Documentation

For further reference, please consider the following sections:

- [Transbase Getting Started](https://www.transaction.de/fileadmin/public/transbase/8.4/docu/getstart.xhtml)
- [Transbase JDBC](https://www.transaction.de/fileadmin/public/transbase/8.4/docu/jdbc.xhtml)
