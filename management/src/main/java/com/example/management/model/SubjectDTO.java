package com.example.management.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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



