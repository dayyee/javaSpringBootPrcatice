package com.example.management.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SubjectDTO extends UserDTO{
    private Integer id;
    private String name;
    private Integer code;
    private String title;

}



