package com.example.downloader.services;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.models.NameCourse;
import com.example.downloader.repositories.CourseRepository;
import com.example.downloader.repositories.GroupCourseRepository;
import com.example.downloader.repositories.NameCourseRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyService {

    private static final Logger logger = LoggerFactory.getLogger(ModifyService.class);

    public final CourseRepository courseRepository;
    public final GroupCourseRepository groupCourseRepository;
    public final NameCourseRepository nameCourseRepository;

    @Autowired
    public ModifyService(CourseRepository courseRepository,
                         GroupCourseRepository groupCourseRepository,
                         NameCourseRepository nameCourseRepository) {
        this.courseRepository = courseRepository;
        this.groupCourseRepository = groupCourseRepository;
        this.nameCourseRepository = nameCourseRepository;
    }

    public void insertCourses(List<Course> courses) {
        int count = 0;
        for (Course course : courses) {
            if (++count % 100 == 0) {
                logger.info("Inserted {} courses", count);
            }
            String subject = course.getSubject();
            String year = course.getYear();
            String name = course.getName();

            var groupCourse = groupCourseRepository.findBySubjectAndYear(subject, year).orElse(
                GroupCourse.builder().subject(subject).year(year).courses(new ArrayList<>())
                    .build());

            var nameCourse = nameCourseRepository.findByName(name).orElse(
                NameCourse.builder().name(name).courses(new ArrayList<>()).build());

            var realCourse = courseRepository.save(course);
            groupCourse.getCourses().add(realCourse);
            nameCourse.getCourses().add(realCourse);
            groupCourseRepository.save(groupCourse);
            nameCourseRepository.save(nameCourse);
            realCourse.setGroupCourse(groupCourse);
            realCourse.setNameCourse(nameCourse);
            courseRepository.save(realCourse);
        }
    }

    @Transactional
    public void deleteAllCourses() {
        courseRepository.deleteAll();
        groupCourseRepository.deleteAll();
        nameCourseRepository.deleteAll();
        courseRepository.alterSequenceToOne();
        groupCourseRepository.alterSequenceToOne();
        nameCourseRepository.alterSequenceToOne();
    }
}