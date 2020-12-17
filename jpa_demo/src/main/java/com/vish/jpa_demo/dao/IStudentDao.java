package com.vish.jpa_demo.dao;

import com.vish.jpa_demo.model.Student;

public interface IStudentDao
{
    void addStudent(Student student);

    Student getStudentDetailsByName(String name);
}
