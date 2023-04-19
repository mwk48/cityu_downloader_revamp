package com.example.downloader;

import com.example.downloader.models.Course;
import com.example.downloader.services.CourseService;
import com.example.downloader.services.GroupCourseService;
import com.example.downloader.services.ModifyService;
import com.example.downloader.services.NameCourseService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(initializers = {DbInitializer.class})
@Transactional
class ServiceTest extends BaseDbTest {


    private final CourseService courseService;

    private final GroupCourseService groupCourseService;

    private final NameCourseService nameCourseService;

    @Autowired
    public ServiceTest(
        CourseService courseService,
        GroupCourseService groupCourseService,
        NameCourseService nameCourseService,
        ModifyService modifyService) {
        super(modifyService);
        this.courseService = courseService;
        this.groupCourseService = groupCourseService;
        this.nameCourseService = nameCourseService;
    }


    @Test
    void testNumberOfCourses() {
        Assertions.assertEquals(courseService.getCourseCount(), limit);
        Assertions.assertEquals(nameCourseService.getNameCourseCount(), 9);
        Assertions.assertEquals(groupCourseService.getGroupCourseCount(), 3);
    }

    @Test
    void testNumberOfCoursesByYears() {
        List<Course> cs19961997A =
            groupCourseService.getAllCoursesBySubjectAndYear("CS", "1996-1997A");

        List<Course> cs19961997B =
            groupCourseService.getAllCoursesBySubjectAndYear("CS", "1996-1997B");

        List<Course> cs19961997S =
            groupCourseService.getAllCoursesBySubjectAndYear("CS", "1996-1997S");

        Assertions.assertEquals(cs19961997A.size() + cs19961997B.size() + cs19961997S.size(),
            limit);
    }

    @Test
    void testNumberOfYearsBySubject() {
        List<String> csYears =
            groupCourseService.getYears("CS");

        Assertions.assertEquals(csYears.size(), 3);
    }

    @Test
    void testNumberOfSubjectsByYear() {
        List<String> cs19961997aYears =
            groupCourseService.getSubjects("1996-1997A");

        List<String> cs19961997bYears =
            groupCourseService.getSubjects("1996-1997B");

        List<String> cs19961997sYears =
            groupCourseService.getSubjects("1996-1997S");

        Assertions.assertEquals(cs19961997aYears.size(), 1);
        Assertions.assertEquals(cs19961997bYears.size(), 1);
        Assertions.assertEquals(cs19961997sYears.size(), 1);
    }

    @Test
    void testAllSubjects() {
        List<String> allSubjects = groupCourseService.getAllSubjects();
        Assertions.assertIterableEquals(allSubjects, List.of("CS"));
    }

    @Test
    void testAllYears() {
        List<String> allSubjects = groupCourseService.getAllYears();
        Assertions.assertIterableEquals(allSubjects,
            List.of("1996-1997A", "1996-1997B", "1996-1997S"));
    }

    @Test
    @DisplayName("Test first course, ...1996-1997/CS/CS0242-9697B.pdf")
    void testFirstCourse() {
        Course course = courseService.getCourseById(1L);
        Assertions.assertEquals(course.getName(), "CS0242");
        Assertions.assertEquals(course.getSubject(), "CS");
        Assertions.assertEquals(course.getYear(), "1996-1997B");
        Assertions.assertEquals(course.getUrl(),
            "http://lbdata.cityu.edu.hk.ezproxy.cityu.edu.hk/exam_paper/1996-1997/CS/CS0242-9697B.pdf");
        Assertions.assertEquals(course.getGroupCourse().getId(), 1L);
        Assertions.assertEquals(course.getNameCourse().getId(), 1L);

    }
    

}
