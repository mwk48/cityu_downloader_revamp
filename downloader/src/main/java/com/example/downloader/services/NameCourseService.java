package com.example.downloader.services;

import com.example.downloader.exceptions.NotFoundException;
import com.example.downloader.models.Course;
import com.example.downloader.models.NameCourse;
import com.example.downloader.repositories.NameCourseRepository;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class NameCourseService {

    private final NameCourseRepository nameCourseRepository;

    public NameCourseService(NameCourseRepository nameCourseRepository) {
        this.nameCourseRepository = nameCourseRepository;
    }

    @Cacheable(value = "name-courses", key = "#name")
    public List<Course> getAllCoursesByName(String name) {
        return nameCourseRepository.findByName(name).orElseThrow(
                () -> new NotFoundException(String.format("NameCourse with name %s not found", name)))
            .getCourses();
    }

    @Cacheable("name-courses-all")
    public List<NameCourse> getAllNameCourses() {
        return nameCourseRepository.findAll();
    }

    public Long getNameCourseCount() {
        return nameCourseRepository.count();
    }
}
