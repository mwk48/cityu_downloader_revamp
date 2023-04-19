package com.example.downloader;

import com.example.downloader.models.Course;
import com.example.downloader.services.InsertService;
import com.example.downloader.utils.ParseUtils;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseDBTest {

    protected final InsertService insertService;


    BaseDBTest(InsertService insertService) {
        this.insertService = insertService;
    }

    @BeforeAll
    void addCourses() throws FileNotFoundException {
        System.out.println("Adding courses");
        List<Course> courses = ParseUtils.getAllCourses(10);
        insertService.insertCourses(courses);
    }
}
