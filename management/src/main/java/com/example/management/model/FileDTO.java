package com.example.management.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class FileDTO {
    private String fileName;
    private long fileSize;
    private Instant lastModified;

    //list로 새로 만들기 위해서 이런게 필요..
    public FileDTO(String fileName, long fileSize, Instant lastModified) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.lastModified = lastModified;
    }
}
