package com.example.downloader.repositories;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupCourseRepository extends JpaRepository<GroupCourse, Long> {


    Optional<GroupCourse> findBySubjectAndYear(String subject, String year);

    @Query("SELECT c FROM Course c left join GroupCourse g on c.groupCourse.id = g.id WHERE g.id = ?1")
    List<Course> findAllCourses(Long id);

    List<GroupCourse> findAllByYear(String year);

    List<GroupCourse> findAllBySubject(String subject);

    @Query("SELECT DISTINCT year FROM GroupCourse order by year asc")
    List<String> findAllYears();

    @Query("SELECT DISTINCT subject FROM GroupCourse order by subject asc")
    List<String> findAllSubjects();
}
