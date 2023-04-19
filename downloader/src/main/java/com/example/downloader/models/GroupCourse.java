package com.example.downloader.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "group_course")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class GroupCourse {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "group_course_id_seq"
    )
    @SequenceGenerator(
        name = "group_course_id_seq",
        sequenceName = "group_course_id_seq",
        allocationSize = 1
    )
    Long id;

    @Column(name = "subject")
    String subject;

    @Column(name = "year")
    String year;

    @OneToMany(mappedBy = "groupCourse", fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Course> courses;

}
