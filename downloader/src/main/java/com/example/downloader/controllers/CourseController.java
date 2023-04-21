package com.example.downloader.controllers;

import com.example.downloader.models.Course;
import com.example.downloader.services.CourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {


    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/{id}")

    public Course getCourseById(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @GetMapping(path = "/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}

