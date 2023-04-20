package com.example.downloader.repositories;

import com.example.downloader.models.Course;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query(value = "ALTER SEQUENCE course_id_seq RESTART WITH 1", nativeQuery = true)
    @Modifying
    void alterSequenceToOne();

    @Override
    List<Course> findAll();
}
