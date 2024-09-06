package com.example.management.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsSqsConfig {

    @Value("${aws.sqs.accessKeyId}") // application.properties 값 읽어오기
    private String accessKeyId;

    @Value("${aws.sqs.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public SqsClient sqsClient(){
        return SqsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId,secretAccessKey)
                )).build();
    }
}
