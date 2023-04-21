package com.example.downloader.services;

import com.example.downloader.exceptions.NotFoundException;
import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.repositories.GroupCourseRepository;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GroupCourseService {

    private final GroupCourseRepository groupCourseRepository;

    public GroupCourseService(GroupCourseRepository groupCourseRepository) {
        this.groupCourseRepository = groupCourseRepository;
    }

    @Cacheable(value = "group-courses", key = "#subject + #year")
    public List<Course> getAllCoursesBySubjectAndYear(String subject, String year) {
        return groupCourseRepository.findBySubjectAndYear(subject, year).orElseThrow(
                () -> new NotFoundException(
                    String.format("GroupCourse with subject %s and year %s not found", subject, year)))
            .getCourses();
    }

    @Cacheable("group-courses-all")
    public List<GroupCourse> getAllGroupCourses() {
        return groupCourseRepository.findAll();
    }

    @Cacheable(value = "subjects", key = "#year")
    public List<String> getSubjects(String year) {
        return groupCourseRepository.findSubjectsByYear(year);
    }

    @Cacheable(value = "years", key = "#subject")
    public List<String> getYears(String subject) {
        return groupCourseRepository.findYearsBySubject(subject);
    }

    @Cacheable("subjects-all")
    public List<String> getAllSubjects() {
        return groupCourseRepository.findAllSubjects();
    }

    @Cacheable("years-all")
    public List<String> getAllYears() {
        return groupCourseRepository.findAllYears();
    }

    public Long getGroupCourseCount() {
        return groupCourseRepository.count();
    }
}
