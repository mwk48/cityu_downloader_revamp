package com.example.downloader.configs;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.models.NameCourse;
import com.example.downloader.repositories.CourseRepository;
import com.example.downloader.repositories.GroupCourseRepository;
import com.example.downloader.repositories.NameCourseRepository;
import com.example.downloader.utils.ParseUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initialization implements CommandLineRunner {

    @Value("${app.import-data:false}")
    private Boolean importData;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Initialization.class);
    private final CourseRepository courseRepository;

    private final GroupCourseRepository groupCourseRepository;

    private final NameCourseRepository nameCourseRepository;

    @Autowired
    public Initialization(CourseRepository courseRepository,
                          GroupCourseRepository groupCourseRepository,
                          NameCourseRepository nameCourseRepository) {
        this.courseRepository = courseRepository;
        this.groupCourseRepository = groupCourseRepository;
        this.nameCourseRepository = nameCourseRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        logger.info("Import data: {}", importData);
        if (!importData) {
            logger.info(courseRepository.findAll().size() + " courses");
            return;
        }
        List<Course> courses = ParseUtils.getAllCourses(Integer.MAX_VALUE);
        int count = 0;
        for (Course course : courses) {
            if (++count % 100 == 0) {
                logger.info("Imported {} courses", count);
            }
            String subject = course.getSubject();
            String year = course.getYear();
            String name = course.getName();

            var groupCourse = groupCourseRepository.findBySubjectAndYear(subject, year).orElse(
                GroupCourse.builder().subject(subject).year(year).courses(new ArrayList<>()).build());

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

}
