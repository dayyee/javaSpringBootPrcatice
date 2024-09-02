package com.example.management.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class User {
private int id;
private String name;
private int age;
private String email;
private String phoneNum;
}
