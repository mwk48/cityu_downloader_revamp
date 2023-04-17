package com.example.downloader.services;

import com.example.downloader.models.Course;
import com.example.downloader.repositories.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {


    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Course with id %d not found", id)));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
