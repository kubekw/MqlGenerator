package com.example.application;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PersonsNames {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;



    public PersonsNames() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonsNames(String name) {
        this.name = name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersonsNames{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
