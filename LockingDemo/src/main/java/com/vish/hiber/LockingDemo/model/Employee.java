package com.vish.hiber.LockingDemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
@Table(name="employee")
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @NonNull
    @Column(name = "eid")
    String eid;

    @Column(name = "ename")
    String ename;

    @Column(name = "tech")
    String tech;

    public Employee(String eid, String ename, String tech)
    {
        this.eid = eid;
        this.ename = ename;
        this.tech = tech;

    }

    @Override
    public String toString()
    {
        return "Employee{" +
                "id=" + id +
                ", eid='" + eid + '\'' +
                ", ename='" + ename + '\'' +
                ", tech='" + tech + '\'' +
                '}';
    }
}