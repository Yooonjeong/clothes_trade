package com.elice.boardproject.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Long loginId;
    private Long password;

    public User(){}
    public User(Long loginId, Long password){
        this.loginId = loginId;
        this.password = password;
    }
}
