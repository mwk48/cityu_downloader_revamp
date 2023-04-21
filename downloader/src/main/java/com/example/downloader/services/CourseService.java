package com.example.downloader.services;

import com.example.downloader.exceptions.NotFoundException;
import com.example.downloader.models.Course;
import com.example.downloader.repositories.CourseRepository;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {


    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Cacheable(value = "course", key = "#id")
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(
            () -> new NotFoundException(String.format("Course with id %d not found", id)));
    }

    @Cacheable("courses-all")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Long getCourseCount() {
        return courseRepository.count();
    }

}
