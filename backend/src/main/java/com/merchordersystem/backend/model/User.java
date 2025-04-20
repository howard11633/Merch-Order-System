package com.merchordersystem.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id //PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自動生成
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;

//    @Column(name ="gender")
//    private String gender;
//
//    @Column(name ="email")
//    private String email;
//
//    @Column(name ="role")
//    private String role;
//
//    @Column(name ="password")
//    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
