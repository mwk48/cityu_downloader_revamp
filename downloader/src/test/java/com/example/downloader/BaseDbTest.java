package com.example.downloader;

import com.example.downloader.models.Course;
import com.example.downloader.services.ModifyService;
import com.example.downloader.utils.ParseUtils;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {BaseDbTest.DbInitializer.class})
abstract class BaseDbTest {

    protected final ModifyService modifyService;

    protected int limit = 10;


    BaseDbTest(ModifyService modifyService) {
        this.modifyService = modifyService;
    }


    @BeforeAll
    void addCourses() throws FileNotFoundException {
        System.out.println("Adding courses");
        List<Course> courses = ParseUtils.getAllCourses(limit);
        modifyService.insertCourses(courses);
    }

    @AfterAll
    void deleteCourses() {
        System.out.println("Deleting courses");
        modifyService.deleteAllCourses();
    }

    static class DbInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static final PostgreSQLContainer postgres;

        static final GenericContainer redis;

        static {
            postgres = new PostgreSQLContainer("postgres:14.7")
                .withDatabaseName("test_downloader")
                .withUsername("abc")
                .withPassword("123");
            redis = new GenericContainer("redis:6.2.12-alpine")
                .withExposedPorts(6379);
            postgres.start();
            redis.start();
        }

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues
                .of("spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword(),
                    "spring.data.redis.host=" + redis.getHost(),
                    "spring.data.redis.port=" + redis.getFirstMappedPort())
                .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
