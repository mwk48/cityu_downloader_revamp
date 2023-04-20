package com.example.downloader.controllers;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.services.GroupCourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group-courses")
public class GroupCourseController {

    private final GroupCourseService groupCourseService;

    @Autowired
    public GroupCourseController(GroupCourseService groupCourseService) {
        this.groupCourseService = groupCourseService;
    }

    @GetMapping(path = "/courses", params = {"subject", "year"})
    @Cacheable(value = "group-courses", key = "#subject + #year")
    public List<Course> getGroupCourses(@RequestParam("subject") String subject,
                                        @RequestParam("year") String year) {
        return groupCourseService.getAllCoursesBySubjectAndYear(subject, year);
    }

    @GetMapping(path = "/all")
    @Cacheable("group-courses-all")
    public List<GroupCourse> getAllGroupCourses() {
        return groupCourseService.getAllGroupCourses();
    }


    @GetMapping(path = "/subjects", params = {"year"})
    @Cacheable(value = "subjects", key = "#year")
    public List<String> getSubjects(@RequestParam("year") String year) {
        return groupCourseService.getSubjects(year);
    }

    @GetMapping(path = "/years", params = {"subject"})
    @Cacheable(value = "years", key = "#subject")
    public List<String> getYears(@RequestParam("subject") String subject) {
        return groupCourseService.getYears(subject);
    }

    @GetMapping(path = "/all/subjects")
    @Cacheable("subjects-all")
    public List<String> getAllSubjects() {
        return groupCourseService.getAllSubjects();
    }

    @GetMapping(path = "/all/years")
    @Cacheable("years-all")
    public List<String> getAllYears() {
        return groupCourseService.getAllYears();
    }
}
