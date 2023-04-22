package com.example.downloader.controllers;

import com.example.downloader.exceptions.ApiException;
import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import com.example.downloader.services.GroupCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group-courses")
public class GroupCourseController {

    private final GroupCourseService groupCourseService;

    public GroupCourseController(GroupCourseService groupCourseService) {
        this.groupCourseService = groupCourseService;
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
    @Operation(summary = "Get courses by subject and year")
    @GetMapping(path = "/courses", params = {"subject", "year"})

    public List<Course> getGroupCourses(
        @Schema(type = "string", example = "CS") @RequestParam("subject") String subject,
        @Schema(type = "string", example = "1996-1997A") @RequestParam("year") String year) {
        return groupCourseService.getAllCoursesBySubjectAndYear(subject, year);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = GroupCourse.class)))),
    })
    @Operation(summary = "Get all group courses")
    @GetMapping(path = "/all")

    public List<GroupCourse> getAllGroupCourses() {
        return groupCourseService.getAllGroupCourses();
    }


    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiException.class))),
    })
    @Operation(summary = "Get subjects by year")
    @GetMapping(path = "/subjects", params = {"year"})

    public List<String> getSubjects(
        @Schema(type = "string", example = "1996-1997A") @RequestParam(value = "year")
        String year) {
        return groupCourseService.getSubjects(year);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiException.class))),
    })
    @Operation(summary = "Get years by subject")
    @GetMapping(path = "/years", params = {"subject"})

    public List<String> getYears(
        @Schema(type = "string", example = "CS") @RequestParam("subject") String subject) {
        return groupCourseService.getYears(subject);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
    })
    @Operation(summary = "Get all subjects")
    @GetMapping(path = "/all/subjects")

    public List<String> getAllSubjects() {
        return groupCourseService.getAllSubjects();
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
    })
    @Operation(summary = "Get all years")
    @GetMapping(path = "/all/years")

    public List<String> getAllYears() {
        return groupCourseService.getAllYears();
    }
}
