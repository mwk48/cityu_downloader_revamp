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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        modifyService.deteleAllCourses();
    }
}
