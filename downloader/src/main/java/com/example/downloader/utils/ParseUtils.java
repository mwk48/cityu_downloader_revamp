package com.example.downloader.utils;

import com.example.downloader.models.Course;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParseUtils {

    private static Course parseUrl(String url) {

        var arr = url.split("/");
        String year = arr[arr.length - 3];
        String preFix = arr[arr.length - 1].replaceAll(".pdf", "");
        String semester = preFix.substring(preFix.length() - 1);
        String courseName = arr[arr.length - 1].split("-")[0];
        String subject = arr[arr.length - 2].toUpperCase();
        return Course.builder().url(url).year(year + semester).name(courseName).subject(subject)
            .build();
    }

    public static List<Course> getAllCourses(int limit) throws FileNotFoundException {
        var file = new FileReader("src/main/resources/file.txt");
        Scanner scanner = new Scanner(file);
        List<Course> courses = new ArrayList<>();
        while (limit-- > 0 && scanner.hasNextLine()) {
            var line = scanner.nextLine();
            var course = ParseUtils.parseUrl(line);
            courses.add(course);
        }
        return courses;
    }


}