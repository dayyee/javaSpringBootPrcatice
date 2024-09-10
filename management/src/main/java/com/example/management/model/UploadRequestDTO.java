package com.example.management.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class UploadRequestDTO {
    private String fileTitle;
    private List<UserDTO> userList;
}
