package com.example.downloader.configs;

import com.example.downloader.models.Course;
import com.example.downloader.repositories.CourseRepository;
import com.example.downloader.repositories.GroupCourseRepository;
import com.example.downloader.repositories.NameCourseRepository;
import com.example.downloader.utils.CourseUtils;
import com.example.downloader.utils.ParseUtils;
import jakarta.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class Initialization implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Initialization.class);
    private final CourseRepository courseRepository;
    private final GroupCourseRepository groupCourseRepository;
    private final NameCourseRepository nameCourseRepository;
    @Value("${app.import-data:false}")
    private Boolean importData;

    @Value("${app.delete-data:false}")
    private Boolean deleteData;

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
        deleteCourses();
        insertCourses();
    }

    private void deleteCourses() {
        logger.info("Delete data: {}", deleteData);
        if (deleteData) {
            courseRepository.deleteAll();
            groupCourseRepository.deleteAll();
            nameCourseRepository.deleteAll();
        }
    }

    private void insertCourses() throws FileNotFoundException {
        logger.info("Import data: {}", importData);
        long count = courseRepository.count();
        if (!importData || count > 0) {
            logger.info(count + " courses");
            return;
        }
        List<Course> courses = ParseUtils.getAllCourses(Integer.MAX_VALUE);
        CourseUtils.insertAllCourses(courses, courseRepository, groupCourseRepository,
            nameCourseRepository);
    }
}
