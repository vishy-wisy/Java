package com.vish.jpa_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@Table(name="Laptop")
public class Laptop
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lid")
    private long laptop_id;

    @Column
    private String brand;

    public Laptop(String brand)
    {
        this.brand = brand;
    }
}
