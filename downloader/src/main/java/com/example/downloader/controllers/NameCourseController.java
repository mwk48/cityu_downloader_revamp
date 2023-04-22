package com.example.downloader.controllers;

import com.example.downloader.exceptions.ApiException;
import com.example.downloader.models.Course;
import com.example.downloader.models.NameCourse;
import com.example.downloader.services.NameCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/name-courses")
public class NameCourseController {

    private final NameCourseService nameCourseService;

    @Autowired
    public NameCourseController(NameCourseService nameCourseService) {
        this.nameCourseService = nameCourseService;
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Course.class)))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiException.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiException.class))),
    })
    @Operation(summary = "Get courses by name")
    @GetMapping(path = "/courses", params = {"name"})

    public List<Course> getGroupCourses(
        @Schema(type = "string", example = "CS0242") @RequestParam("name") String name) {
        return nameCourseService.getAllCoursesByName(name);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = NameCourse.class)))),
    })
    @Operation(summary = "Get all name courses")
    @GetMapping(path = "/all")

    public List<NameCourse> getAllNameCourses() {
        return nameCourseService.getAllNameCourses();
    }
}
