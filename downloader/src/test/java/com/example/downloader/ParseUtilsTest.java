package com.example.downloader;


import com.example.downloader.models.Course;
import com.example.downloader.utils.ParseUtils;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParseUtilsTest {

    @Test
    public void testParse() throws FileNotFoundException {

        List<Course> courses = ParseUtils.getAllCourses(10);
        Assertions.assertEquals(10, courses.size());
        var firstCourse = courses.get(0);
        Assertions.assertEquals(firstCourse.getName(), "CS0242");
        Assertions.assertEquals(firstCourse.getSubject(), "CS");
        Assertions.assertEquals(firstCourse.getYear(), "1996-1997B");
        Assertions.assertEquals(firstCourse.getUrl(), "http://lbdata.cityu.edu.hk.ezproxy.cityu.edu.hk/exam_paper/1996-1997/CS/CS0242-9697B.pdf");

    }
}
