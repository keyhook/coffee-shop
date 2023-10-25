# Coffee Shop

Coffee Shop is a platform that allows customers to pre-order coffee to pick up (say, on their way to work).

## Technology stack

- OpenJDK 17
- Spring framework
- Gradle build system
- Docker
- PostgreSql

## Getting Started

### Provision the databases

- For your convenience, we provide a [docker-compose.yml](./docker-compose.yml) file to provision the PostgreSql instance. Please take note the information used in this file because they are also configured accordingly in [application.yml](./src/main/resources/application.yml) file to run this project.

- Provision command:

```shell
$ docker-compose -f ./docker-compose.yml up -d
```

### Build the Coffee Shop platform

```shell
$ ./gradlew clean build
```

### Run the Coffee Shop platform for development purpose

```shell
$ java -jar build/libs/coffee-shop-0.0.1.jar
```

### Build Docker image

```shell
$ docker build -t coffee-shop .
```

### Run the Coffee Shop platform with Docker

```shell
$ docker run coffee-shop

...

24-Oct-2023 02:45:09.546 INFO  [ coffee-shop - main ] o.s.b.w.e.t.TomcatWebServer(220): - Tomcat started on port(s): 8080 (http) with context path ''
24-Oct-2023 02:45:09.555 INFO  [ coffee-shop - main ] o.s.b.StartupInfoLogger(57): - Started CoffeeShop in 4.261 seconds (process running for 4.992)
```

- Please remember to specify the correct environment variables to make the service run properly.

### Initialize schemas & test data

- For Postgresql, we use `Flyway` to initialize the database schema automatically when the Coffee Shop application first bootstrap. You will see a `flyway_schema_history` table to track the schema version in the default `pubic` schema of Postgresql. The schema file locates at [V1__init_db.sql](./src/main/resources/db/migration/V1__init_db.sql).

### Play with the Coffee Shop platform

- Please import the `./Digital101.post_collection.json` file to the Postman tool to play with the features provided by the Marketplace platform.