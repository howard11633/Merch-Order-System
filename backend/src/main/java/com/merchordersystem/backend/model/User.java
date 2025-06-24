package com.merchordersystem.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id //PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自動生成
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name ="gender")
    private Gender gender;

    @Column(name ="email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name ="role")
    private Role role;

    @JsonIgnore
    @Column(name ="password")
    private String password;

    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
