package com.example.downloader.controllers;

import com.example.downloader.models.Course;
import com.example.downloader.models.NameCourse;
import com.example.downloader.services.NameCourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/name-courses")
public class NameCourseController {

    private final NameCourseService nameCourseService;

    @Autowired
    public NameCourseController(NameCourseService nameCourseService) {
        this.nameCourseService = nameCourseService;
    }

    @GetMapping(path = "/courses", params = {"name"})

    public List<Course> getGroupCourses(@RequestParam("name") String name) {
        return nameCourseService.getAllCoursesByName(name);
    }

    @GetMapping(path = "/all")

    public List<NameCourse> getAllNameCourses() {
        return nameCourseService.getAllNameCourses();
    }
}
