package com.example.downloader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.downloader.exceptions.NotFoundException;
import com.example.downloader.services.ModifyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@Transactional
class ApiTest extends BaseDbTest {


    private final MockMvc mockMvc;


    @Autowired
    public ApiTest(ModifyService modifyService, MockMvc mockMvc) {
        super(modifyService);
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Test GET /api/courses/all")
    void testGetCourses() throws Exception {

        mockMvc.perform(get("/api/courses/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("CS0242"))
            .andExpect(jsonPath("$[0].subject").value("CS"))
            .andExpect(jsonPath("$[0].year").value("1996-1997B"))
            .andExpect(jsonPath("$[0].url").value(
                "http://lbdata.cityu.edu.hk.ezproxy.cityu.edu.hk/exam_paper/1996-1997/CS/CS0242-9697B.pdf"))
            .andExpect(jsonPath("$[0].groupCourse").doesNotExist())
            .andExpect(jsonPath("$[0].nameCourse").doesNotExist());
    }

    @Test
    @DisplayName("Test GET /api/courses/{id}")
    void testGetCourseById() throws Exception {

        mockMvc.perform(get("/api/courses/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.groupCourse").doesNotExist())
            .andExpect(jsonPath("$.nameCourse").doesNotExist());

        mockMvc.perform(get("/api/courses/100"))
            .andExpect(status().isNotFound())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }


}
