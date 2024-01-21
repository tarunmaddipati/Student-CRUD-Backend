package com.example.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subject_name")
    private String name;
    @Column(name = "subject_decription")
    private String decription;
    @Column(name = "subject_topic")
    private String topic;

    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();


    public Subject(String name, String decription, String topic) {
        super();
        this.name = name;
        this.decription = decription;
        this.topic = topic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", decription='" + decription + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
