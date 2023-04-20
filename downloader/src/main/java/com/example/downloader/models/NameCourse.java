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
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "name_course")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class NameCourse implements Serializable {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "name_course_id_seq"
    )
    @SequenceGenerator(
        name = "name_course_id_seq",
        sequenceName = "name_course_id_seq",
        allocationSize = 1
    )
    Long id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "nameCourse", fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Course> courses;

}
