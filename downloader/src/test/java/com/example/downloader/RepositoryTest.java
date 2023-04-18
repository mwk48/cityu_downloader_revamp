package com.example.downloader;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.models.NameCourse;
import com.example.downloader.repositories.CourseRepository;
import com.example.downloader.repositories.GroupCourseRepository;
import com.example.downloader.repositories.NameCourseRepository;
import com.example.downloader.utils.CourseUtils;
import com.example.downloader.utils.ParseUtils;
import jakarta.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {DBInitializer.class})
class RepositoryTest {

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

    @BeforeAll
    void addCourses() throws FileNotFoundException {
        System.out.println("Adding courses");
        List<Course> courses = ParseUtils.getAllCourses(10);
        CourseUtils.insertAllCourses(courses, courseRepository, groupCourseRepository,
            nameCourseRepository);
    }

}
