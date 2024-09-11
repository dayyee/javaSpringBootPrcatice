package com.example.management.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@ToString
@Getter
@Setter
public class UserDTO {

    @NotNull(message = "ID는 필수 값입니다.")
    private Integer id;

    @NotBlank(message = "이름은 필수 값입니다.")
    @Size(min=2, max=30, message = "이름은 2자 이상, 30자 이하여야 합니다.")
    private String name;

    private Integer age;

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식이 아닙니다.")
    private String phoneNum;
}
