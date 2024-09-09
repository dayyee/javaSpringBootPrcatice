package com.example.management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class AwsS3Config {
    @Value("${aws.s3.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.s3.secretAccessKey}")
    private String secretAccessKey;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId,secretAccessKey)
                )).build();
    }

    @Bean
    public String bucketName(){
        return bucketName;
    }

}
