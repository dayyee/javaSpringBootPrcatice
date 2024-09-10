package com.example.management.controller;

import com.example.management.aws.AwsS3Service;
import com.example.management.model.FileDTO;
import com.example.management.model.UploadRequestDTO;
import com.example.management.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;

@CrossOrigin(origins="http://localhost:8090")
@RequestMapping("/files")
@RestController
public class FileController {
    private final AwsS3Service awsS3Service;

    public FileController(AwsS3Service awsS3Service){
        this.awsS3Service=awsS3Service;
    }

    // file list 불러오기
    @GetMapping("/list")
    public List<FileDTO> listObject(){
        return awsS3Service.listBucketObjects();
    }

    // file upload
    @PostMapping("/upload")
    public ResponseEntity<String> putData(@RequestBody UploadRequestDTO uploadReq){
        String fileTitle= uploadReq.getFileTitle();
        List<UserDTO> userList = uploadReq.getUserList();
        Integer result = awsS3Service.uploadListTos3Object(fileTitle, userList);
        if(result > 0) {
            return ResponseEntity.ok("파일 업로드가 완료되었습니다.");
        }else{
            return ResponseEntity.status(404).body("파일 업로드를 실패하였습니다.");
        }
    }
}
