package com.vish.jpa_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@Table(name="Student")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private long student_id;
    @Column
    private String sname;

    @Column
    private int marks=0;

    @OneToOne
    private Laptop laptop;

    public Student(String sname)
    {
        this.sname = sname;
    }

}
