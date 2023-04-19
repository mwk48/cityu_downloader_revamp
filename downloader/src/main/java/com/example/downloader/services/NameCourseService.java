package com.example.downloader.services;

import com.example.downloader.models.Course;
import com.example.downloader.models.NameCourse;
import com.example.downloader.repositories.NameCourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NameCourseService {

    private final NameCourseRepository nameCourseRepository;

    @Autowired
    public NameCourseService(NameCourseRepository nameCourseRepository) {
        this.nameCourseRepository = nameCourseRepository;
    }

    public List<Course> getAllCoursesByName(String name) {
        return nameCourseRepository.findByName(name).orElseThrow(
                () -> new RuntimeException(String.format("NameCourse with name %s not found", name)))
            .getCourses();
    }

    public List<NameCourse> getAllNameCourses() {
        return nameCourseRepository.findAll();
    }

    public Long getNameCourseCount() {
        return nameCourseRepository.count();
    }
}
