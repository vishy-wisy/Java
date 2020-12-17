package com.vish.jpa_demo.dao;

import com.vish.jpa_demo.model.Laptop;

public interface ILaptopDao
{
    void addLaptop(Laptop laptop);

    Laptop getLaptopDetailsByName(String name);
}
