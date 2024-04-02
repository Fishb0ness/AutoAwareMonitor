package com.fishb0ness.autoawaremonitor.adapter;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(initializers = {IntegrationTest.Initializer.class})
public abstract class IntegrationTest {

    private static final int MONGO_PORT = 27017;
    private static final String MONGO_DB_NAME = "testDB";
    private static final String MONGO_DB_USERNAME = "TestRoot";
    private static final String MONGO_DB_PASSWORD = "TestPassword";
    private static final MongoDBContainer mongoDBContainer;

    static {
        mongoDBContainer = new MongoDBContainer("mongo:4.0.10")
                .withExposedPorts(MONGO_PORT)
                .withEnv("MONGO_INITDB_ROOT_USERNAME", MONGO_DB_USERNAME)
                .withEnv("MONGO_INITDB_ROOT_PASSWORD", MONGO_DB_PASSWORD)
                .withEnv("MONGO_INITDB_DATABASE", MONGO_DB_NAME)
                .withCommand("--auth")
                .withStartupAttempts(4);
    }


    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            mongoDBContainer.start();

            TestPropertyValues.of(
                    "mongo.port=" + mongoDBContainer.getMappedPort(MONGO_PORT),
                    "mongo.database=" + MONGO_DB_NAME,
                    "mongo.username=" + MONGO_DB_USERNAME,
                    "mongo.password=" + MONGO_DB_PASSWORD
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
