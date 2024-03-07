package com.mmt.mmtApp.models;//package com.example.springboot;

import jakarta.persistence.*;
@Entity
@Table(name = "citydata")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    @Override
    public String toString() {

        return "Route{" +
                "id=" + id +
                ",City=" +name +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    // Constructors, getters, setters
}