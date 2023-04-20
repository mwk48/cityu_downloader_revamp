package com.example.downloader.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Table(name = "course")
@Builder
public class Course implements Serializable {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "course_id_seq"
    )
    @SequenceGenerator(
        name = "course_id_seq",
        sequenceName = "course_id_seq",
        allocationSize = 1
    )
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "subject")
    String subject;

    @Column(name = "year")
    String year;

    @Column(name = "url")
    String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "name_course_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnoreProperties("courses")
    NameCourse nameCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "group_course_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnoreProperties("courses")
    GroupCourse groupCourse;

    public void setGroupCourse(GroupCourse groupCourse) {
        this.groupCourse = groupCourse;
    }

    public void setNameCourse(NameCourse nameCourse) {
        this.nameCourse = nameCourse;
    }
}
