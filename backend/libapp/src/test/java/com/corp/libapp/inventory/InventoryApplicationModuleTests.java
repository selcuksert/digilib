package com.corp.libapp.inventory;

import com.corp.libapp.inventory.event.InventoryUpdated;
import com.corp.libapp.inventory.event.Status;
import com.corp.libapp.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.EnableScenarios;
import org.springframework.modulith.test.Scenario;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ApplicationModuleTest
@EnableScenarios
@Testcontainers(disabledWithoutDocker = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class InventoryApplicationModuleTests {

    private static final String ISBN = "9789750718533";

    private final InventoryService inventoryService;

    @Test
    void testsInventoryUpdatedEvent(Scenario scenario) {
        scenario.stimulate(() -> inventoryService.deleteBookByISBN(ISBN))
                .andWaitForEventOfType(InventoryUpdated.class)
                .matchingMappedValue(InventoryUpdated::isbn, ISBN)
                .toArriveAndVerify(event -> assertEquals(0, event.status().compareTo(Status.DELETED)));
    }

    @TestConfiguration
    static class InfrastructureConfiguration {

        @Bean
        @ServiceConnection
        Neo4jContainer<?> neo4jContainer() {
            try (Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:5-community")) {
                return container.withRandomPassword();
            }
        }

        @Bean
        Driver driver(Neo4jContainer<?> container) {
            return GraphDatabase.driver(container.getBoltUrl(), AuthTokens.basic("neo4j", container.getAdminPassword()));
        }

        @Bean
        Configuration cypherDslConfiguration() {
            return Configuration.newConfig().withDialect(Dialect.NEO4J_5).withPrettyPrint(true).build();
        }

        @Bean
        @ServiceConnection
        PostgreSQLContainer<?> postgreSQLContainer() {
            try (PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16-alpine")) {
                return container.withDatabaseName("appdb").withUsername("dbuser").withPassword("1q2w3e4r:!");
            }
        }

        @Bean
        @ServiceConnection
        KafkaContainer kafkaContainer() {
            try (KafkaContainer container = new KafkaContainer(DockerImageName.parse("confluentinc/confluent-local:latest")
                    .asCompatibleSubstituteFor("confluentinc/cp-kafka")).withKraft()) {
                return container.withClusterId("QjkzODg0QzYxMTUxNEZEMD");
            }
        }
    }
}
