package com.example.downloader.repositories;

import com.example.downloader.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long> {


}
