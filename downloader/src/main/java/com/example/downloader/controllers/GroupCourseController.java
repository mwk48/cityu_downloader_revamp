package com.example.downloader.controllers;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.services.GroupCourseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Course> getGroupCourses(@RequestParam("subject") String subject,
                                        @RequestParam("year") String year) {
        return groupCourseService.getAllCoursesBySubjectAndYear(subject, year);
    }

    @GetMapping(path = "/all")
    public List<GroupCourse> getAllGroupCourses() {
        return groupCourseService.getAllGroupCourses();
    }

    @GetMapping(path = "/subjects", params = {"year"})
    public List<String> getSubjects(@RequestParam("year") String year) {
        return groupCourseService.getSubjects(year);
    }

    @GetMapping(path = "/years", params = {"subject"})
    public List<String> getYears(@RequestParam("subject") String subject) {
        return groupCourseService.getYears(subject);
    }

    @GetMapping(path = "/all/subjects")
    public List<String> getAllSubjects() {
        return groupCourseService.getAllSubjects();
    }

    @GetMapping(path = "/all/years")
    public List<String> getAllYears() {
        return groupCourseService.getAllYears();
    }
}
