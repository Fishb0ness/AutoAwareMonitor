# AutoAwareMonitor
[Español](./README_ES.md)

AutoAwareMonitor is a vehicle monitoring application developed with Spring Boot, MongoDB, and Gradle. The application is based on a mix of Domain-Driven Design (DDD) and Hexagonal Architecture (HEX).

## Architecture

This project follows a mix of Domain-Driven Design (DDD) and Hexagonal Architecture (HEX). 

DDD is an approach to software development that centers the development on programming a domain model that has a rich understanding of the processes and rules of a domain. This approach is typically used for complex systems where the domain itself is complex.

Hexagonal Architecture, also known as Ports and Adapters, is a design pattern used in software application development. It aims at creating loosely coupled application components that can be easily connected to their software environment by means of ports and adapters. This makes the system components exchangeable at any level and facilitates test automation.

## Requirements

- Java 17
- Gradle
- Docker
- MongoDB

## Setup

To set up the project, follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run `gradle build` to compile the project.

## Database Execution

To run the MongoDB database, you will need Docker and Docker Compose installed on your machine. Once you have them installed, you can start the database with the following command:

```bash
docker-compose -f compose.yaml up
```

## Execution

To run the project, follow these steps:

1. Make sure MongoDB is running.
2. Run `gradle bootRun` to start the application.

## Testing

To run the tests, use the `gradle test` command.

## Known Issues

### Error when starting the database for tests

Sometimes, when trying to start the MongoDB database with Docker, the following error may appear: org.testcontainers.containers.MongoDBContainer$ReplicaSetInitializationException

This error indicates that the database could not be initialized correctly in the expected time. This can cause the tests to appear as failed.

#### Solution

If you encounter this error, you can try the following solutions:

1. Make sure Docker is running correctly on your machine.
2. Try to increase the timeout in your Testcontainers configuration.
3. Restart Docker and try to run the tests again.

## Contributing

Contributions are welcome. Please open an issue to discuss what you would like to change.
