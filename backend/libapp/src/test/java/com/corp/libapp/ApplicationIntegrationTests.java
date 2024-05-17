package com.corp.libapp;

import com.corp.libapp.inventory.event.InventoryUpdated;
import com.corp.libapp.inventory.event.Status;
import com.corp.libapp.inventory.service.InventoryService;
import com.corp.libapp.search.service.BookService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.modulith.events.core.EventPublicationRegistry;
import org.springframework.modulith.test.EnableScenarios;
import org.springframework.modulith.test.Scenario;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EnableScenarios
@Testcontainers(disabledWithoutDocker = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ApplicationIntegrationTests {
    private static final String ISBN = "9789750718533";
    private final EventPublicationRegistry registry;
    private final BookService bookService;
    private final InventoryService inventoryService;

    @Test
    @Order(1)
    void publishesBookAddition(Scenario scenario) {
        scenario.stimulate(() -> bookService.addBook(ISBN))
                .andWaitForStateChange(registry::findIncompletePublications, Collection::isEmpty)
                .andExpect(InventoryUpdated.class)
                .matchingMappedValue(InventoryUpdated::isbn, ISBN)
                .matchingMappedValue(InventoryUpdated::status, Status.ADDED)
                .toArriveAndAssert((event, result) -> assertTrue(inventoryService.existsBookByISBN(ISBN)));
    }

    @Test
    @Order(2)
    void publishesBookDeletion(Scenario scenario) {
        scenario.stimulate(() -> bookService.deleteBook(ISBN))
                .andWaitForStateChange(registry::findIncompletePublications, Collection::isEmpty)
                .andExpect(InventoryUpdated.class)
                .matchingMappedValue(InventoryUpdated::isbn, ISBN)
                .matchingMappedValue(InventoryUpdated::status, Status.DELETED)
                .toArriveAndAssert((event, result) -> assertFalse(inventoryService.existsBookByISBN(ISBN)));
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
