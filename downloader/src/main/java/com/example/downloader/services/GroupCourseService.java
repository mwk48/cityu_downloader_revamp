package com.example.downloader.services;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.repositories.GroupCourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupCourseService {

    private final GroupCourseRepository groupCourseRepository;

    @Autowired
    public GroupCourseService(GroupCourseRepository groupCourseRepository) {
        this.groupCourseRepository = groupCourseRepository;
    }

    public List<Course> getAllCoursesBySubjectAndYear(String subject, String year) {
        return groupCourseRepository.findBySubjectAndYear(subject, year).orElseThrow(
                () -> new RuntimeException(
                    String.format("GroupCourse with subject %s and year %s not found", subject, year)))
            .getCourses();
    }

    public List<GroupCourse> getAllGroupCourses() {
        return groupCourseRepository.findAll();
    }

    public List<String> getSubjects(String year) {
        var courses = groupCourseRepository.findAllByYear(year);
        return courses.stream().map(GroupCourse::getSubject).distinct().sorted().toList();
    }

    public List<String> getYears(String subject) {
        var courses = groupCourseRepository.findAllBySubject(subject);
        return courses.stream().map(GroupCourse::getYear).distinct().sorted().toList();
    }

    public List<String> getAllSubjects() {
        return groupCourseRepository.findAllSubjects();
    }

    public List<String> getAllYears() {
        return groupCourseRepository.findAllYears();
    }
}
