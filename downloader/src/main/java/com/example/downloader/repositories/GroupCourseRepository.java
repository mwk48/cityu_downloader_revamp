package com.example.downloader.repositories;

import com.example.downloader.models.Course;
import com.example.downloader.models.GroupCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GroupCourseRepository extends CrudRepository<GroupCourse, Long> {


    Optional<GroupCourse> findBySubjectAndYear(String subject, String year);

    @Query("SELECT c FROM Course c left join GroupCourse g on c.groupCourse.id = g.id WHERE g.id = ?1")
    List<Course> findAllCourses(Long id);

    @Query("SELECT DISTINCT subject FROM GroupCourse WHERE year = ?1 order by subject asc")
    List<String> findSubjectsByYear(String year);


    @Query("SELECT DISTINCT year FROM GroupCourse WHERE subject = ?1 order by year asc")
    List<String> findYearsBySubject(String subject);

    @Query("SELECT DISTINCT year FROM GroupCourse order by year asc")
    List<String> findAllYears();

    @Query("SELECT DISTINCT subject FROM GroupCourse order by subject asc")
    List<String> findAllSubjects();

    @Query(value = "ALTER SEQUENCE group_course_id_seq RESTART WITH 1", nativeQuery = true)
    @Modifying
    void alterSequenceToOne();

    @Override
    List<GroupCourse> findAll();
}
