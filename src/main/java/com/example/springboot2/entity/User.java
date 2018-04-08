package com.example.springboot2.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by liunn on 2018/4/8.
 */
@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}
