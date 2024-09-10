package com.example.management.aws;

import com.example.management.model.FileDTO;
import com.example.management.model.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AwsS3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public AwsS3Service(S3Client s3Client, String bucketName){
        this.s3Client=s3Client;
        this.bucketName=bucketName; // config에 다 넣어놓고 쓰기위해서 @Bean으로 만들어두고 사용.
    }


    // 전체 bucketlist 출력
    public void listBuckets(){
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));
    }

    // 특정 bucketlist의 object list 출력
    public List<FileDTO> listBucketObjects() {
        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3Client.listObjects(listObjects);
            List<S3Object> objects = res.contents();
            System.out.println("objects" + objects);
            List<FileDTO> result = convertS3ObjectsToFileDTO(objects);
            if(result.isEmpty()){
                return new ArrayList<>();
            }
            return result;

//
//
//            for (S3Object myValue : objects) {
//                System.out.print("\n The name of the key is " + myValue.key()); // 제목
//                System.out.print("\n The object is " + calKb(myValue.size()) + " KBs"); // 크기
//                System.out.print("\n The owner is " + myValue.owner()); //소유자
//            }

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return new ArrayList<>(); // 예외가 발생한 후에도 값 리턴할 수 있게
    }


    private List<FileDTO> convertS3ObjectsToFileDTO(List<S3Object> s3Objects){
        List<FileDTO> fileList= new ArrayList<>();

        for(S3Object s3Object : s3Objects){
            String fileName = s3Object.key();
            long fileSize = s3Object.size();
            Instant lastModified = s3Object.lastModified();

            // FileDTO 객체 생성 및 리스트에 추가
            FileDTO fileDTO = new FileDTO(fileName, fileSize, lastModified);
            fileList.add(fileDTO);
        }
        return fileList;
    }

    // convert bytes to kbs.
    private long calKb(Long val) {
        return val / 1024;
    }

    public Integer uploadListTos3Object(String fileTitle, List<UserDTO> userList) {
        try {
            File file = writeListToCSV(userList, "temp.csv");

            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileTitle)
                    .key(fileTitle)
                    .metadata(metadata)
                    .build();

             PutObjectResponse result = s3Client.putObject(putOb, RequestBody.fromFile(file));
             System.out.println("Successfully placed " + fileTitle + " into bucket " + bucketName);
            return 1;
        } catch (S3Exception | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return 0;
    }

    // 리스트 데이터를 CSV 파일로 저장하는 메서드
    private File writeListToCSV(List<UserDTO> dataList, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        // 헤더 작성 (옵션)
        writer.append("id,name,age,email,phoneNum\n");

        // 리스트의 각 객체의 필드를 CSV 형식으로 작성(txt도 동일함)
        for (UserDTO data : dataList) {
            writer.append(String.valueOf(data.getId()))  // id 필드
                    .append(",")
                    .append(data.getName())  // name 필드
                    .append(",")
                    .append(String.valueOf(data.getAge())) // age 필드
                    .append(",")
                    .append(data.getEmail())  // email 필드
                    .append(",")
                    .append(data.getPhoneNum())
                    .append("\n");
        }

        writer.flush();
        writer.close();

        return new File(filePath); // 저장된 파일을 반환
    }

}
