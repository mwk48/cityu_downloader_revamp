package com.example.downloader.configs;

import com.example.downloader.models.Course;
import com.example.downloader.services.CacheService;
import com.example.downloader.services.CourseService;
import com.example.downloader.services.ModifyService;
import com.example.downloader.utils.ParseUtils;
import java.io.FileNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Initialization implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Initialization.class);
    private final ModifyService modifyService;

    private final CourseService courseService;

    private final CacheService cacheService;


    @Value("${app.import-data:false}")
    private Boolean importData;

    @Value("${app.clear-cache:false}")
    private Boolean clearCache;

    public Initialization(ModifyService modifyService, CourseService courseService,
                          CacheService cacheService) {
        this.modifyService = modifyService;
        this.courseService = courseService;
        this.cacheService = cacheService;
    }

    @Override
    public void run(String... args) throws Exception {
        clearAllCaches();
        insertCourses();
    }


    private void insertCourses() throws FileNotFoundException {
        logger.info("Import data: {}", importData);
        long count = courseService.getCourseCount();
        if (!importData || count > 0) {
            logger.info(count + " courses");
            return;
        }
        List<Course> courses = ParseUtils.getAllCourses(Integer.MAX_VALUE);
        modifyService.insertCourses(courses);
    }

    private void clearAllCaches() {
        logger.info("Clear cache: {}", clearCache);
        if (clearCache) {
            cacheService.clearAllCaches();
        }
    }
}
