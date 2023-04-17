package com.example.downloader;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.models.NameCourse;
import com.example.downloader.repositories.CourseRepository;
import com.example.downloader.repositories.GroupCourseRepository;
import com.example.downloader.repositories.NameCourseRepository;
import com.example.downloader.utils.ParseUtils;
import jakarta.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ContextConfiguration(initializers = {RepositoryTest.Initializer.class})
class RepositoryTest {

    @Container
    public static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres")
        .withDatabaseName("test_downloader")
        .withUsername("abc")
        .withPassword("123");

    static {
        postgres.start();
    }

    private final CourseRepository courseRepository;
    private final GroupCourseRepository groupCourseRepository;
    private final NameCourseRepository nameCourseRepository;

    @Autowired
    public RepositoryTest(CourseRepository courseRepository,
                          GroupCourseRepository groupCourseRepository,
                          NameCourseRepository nameCourseRepository) {
        this.courseRepository = courseRepository;
        this.groupCourseRepository = groupCourseRepository;
        this.nameCourseRepository = nameCourseRepository;
    }

    @Test
    @Transactional
    void testNumberOfCourses() {
        List<Course> courses = courseRepository.findAll();
        List<NameCourse> nameCourses = nameCourseRepository.findAll();
        List<GroupCourse> groupCourses = groupCourseRepository.findAll();
        //System.out.println(courses.size());
        assert courses.size() == 10;
        assert nameCourses.size() == 9;
        assert groupCourses.size() == 3;
    }

    @Test
    @Transactional
    void testRelations() {
        List<Course> courses = courseRepository.findAll();
        List<NameCourse> nameCourses = nameCourseRepository.findAll();
        List<GroupCourse> groupCourses = groupCourseRepository.findAll();
        //System.out.println(courses.size());
        assert courses.size() == 10;
        assert nameCourses.size() == 9;
        assert groupCourses.size() == 3;
    }

    @Transactional
    @BeforeAll
    void addCourses() throws FileNotFoundException {
        List<Course> courses = ParseUtils.getAllCourses(10);
        for (var course : courses) {
            String subject = course.getSubject();
            String year = course.getYear();
            String name = course.getName();

            var groupCourse = groupCourseRepository.findBySubjectAndYear(subject, year).orElse(
                GroupCourse.builder().subject(subject).year(year).courses(new ArrayList<>())
                    .build());

            var nameCourse = nameCourseRepository.findByName(name).orElse(
                NameCourse.builder().name(name).courses(new ArrayList<>()).build());

            var realCourse = courseRepository.save(course);
            groupCourse.getCourses().add(realCourse);
            nameCourse.getCourses().add(realCourse);
            groupCourseRepository.save(groupCourse);
            nameCourseRepository.save(nameCourse);
            realCourse.setGroupCourse(groupCourse);
            realCourse.setNameCourse(nameCourse);
            courseRepository.save(realCourse);
        }
    }

    static class Initializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues
                .of("spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword())
                .applyTo(configurableApplicationContext.getEnvironment());

        }

    }
}
