package com.example.downloader;

import com.example.downloader.services.CourseService;
import com.example.downloader.services.GroupCourseService;
import com.example.downloader.services.InsertService;
import com.example.downloader.services.NameCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(initializers = {DBInitializer.class})
class ServiceTest extends BaseDBTest {


    private final CourseService courseService;

    private final GroupCourseService groupCourseService;

    private final NameCourseService nameCourseService;

    @Autowired
    public ServiceTest(
        CourseService courseService,
        GroupCourseService groupCourseService,
        NameCourseService nameCourseService,
        InsertService insertService) {
        super(insertService);
        this.courseService = courseService;
        this.groupCourseService = groupCourseService;
        this.nameCourseService = nameCourseService;
    }


    @Test
    void testNumberOfCourses() {
        assert courseService.getCourseCount() == 10;
        assert nameCourseService.getNameCourseCount() == 9;
        assert groupCourseService.getGroupCourseCount() == 3;
    }
}
