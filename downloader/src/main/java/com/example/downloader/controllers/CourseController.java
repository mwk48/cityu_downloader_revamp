package com.example.downloader.controllers;

import com.example.downloader.exceptions.ApiException;
import com.example.downloader.models.Course;
import com.example.downloader.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {


    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Course.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiException.class)))
    })
    @Operation(summary = "Get course by id")
    @GetMapping(path = "/{id}")

    public Course getCourseById(
        @Schema(type = "integer", example = "1") @PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @Operation(summary = "Get all courses")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Course.class))))
    })
    @GetMapping(path = "/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}

