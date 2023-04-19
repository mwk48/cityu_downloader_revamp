package com.example.downloader.repositories;

import com.example.downloader.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "ALTER SEQUENCE course_id_seq RESTART WITH 1", nativeQuery = true)
    @Modifying
    void alterSequenceToOne();
}
