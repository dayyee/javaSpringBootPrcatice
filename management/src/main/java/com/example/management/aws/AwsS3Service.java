package com.example.management.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;

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
    public void listBucketObjects() {
        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3Client.listObjects(listObjects);
            List<S3Object> objects = res.contents();
            System.out.println(objects.get(0).key()); //list : .get(index).key() => 첫번째 오브젝트 제목출력
            for (S3Object myValue : objects) {
                System.out.print("\n The name of the key is " + myValue.key()); // 제목
                System.out.print("\n The object is " + calKb(myValue.size()) + " KBs"); // 크기
                System.out.print("\n The owner is " + myValue.owner()); //소유자
            }

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    // convert bytes to kbs.
    private long calKb(Long val) {
        return val / 1024;
    }
}
