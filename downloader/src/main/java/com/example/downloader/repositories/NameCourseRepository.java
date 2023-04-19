package com.example.downloader.repositories;

import com.example.downloader.models.Course;
import com.example.downloader.models.NameCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NameCourseRepository extends JpaRepository<NameCourse, Long> {

    Optional<NameCourse> findByName(String name);

    @Query("SELECT c FROM Course c left join NameCourse n on c.nameCourse.id = n.id WHERE n.id = ?1")
    List<Course> findAllCourses(Long id);

    @Query(value = "ALTER SEQUENCE name_course_id_seq RESTART WITH 1", nativeQuery = true)
    @Modifying
    void alterSequenceToOne();
}
