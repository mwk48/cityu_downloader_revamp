package com.example.downloader;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;


public class DbInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static final PostgreSQLContainer postgres;

    static {
        postgres = new PostgreSQLContainer("postgres:14.7")
            .withDatabaseName("test_downloader")
            .withUsername("abc")
            .withPassword("123");
        postgres.start();


    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        TestPropertyValues
            .of("spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.getUsername(),
                "spring.datasource.password=" + postgres.getPassword())
            .applyTo(configurableApplicationContext.getEnvironment());
    }
}