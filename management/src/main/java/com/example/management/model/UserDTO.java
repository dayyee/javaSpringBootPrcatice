package com.example.management.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserDTO {
private Integer id;
private String name;
private Integer age;
private String email;
private String phoneNum;
}
