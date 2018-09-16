package com.demo.springboot.model;

import java.util.UUID;

public class User {
    private UUID userId;
    private  String firstName;
    private  String lastName;
    private  Gender gender;
    private  Integer age;
    private  String email;


    public enum Gender{
        MALE, FEMALE
    }

    public User(UUID userId, String firstName, String lastName, Gender gender, Integer age, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public User() {
    }

    public void setUserUuid(UUID userUid) {
        this.userId = userUid;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName=" + lastName +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
