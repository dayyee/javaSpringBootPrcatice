package com.example.management.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.concurrent.CompletableFuture;

@Service
public class AwsSnsService {
    private final SnsClient snsClient;

    // config 있어야 주입 가능. 없으면 연결하는것부터 다 적어줘야함.
    public AwsSnsService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    @Value("${aws.sns.topic.arn}")
    private String snsTopicArn;

    // SNS 주제에 메시지를 발행하여 이메일 전송
    public CompletableFuture<Void> sendNotification(String message) {

        return CompletableFuture.runAsync(() -> {
            PublishRequest publishRequest = PublishRequest.builder()
                    .topicArn(snsTopicArn)
                    .message(message)
                    .build();

            snsClient.publish(publishRequest);
            System.out.println("Notifictaion sent to SNS with Message" + message);
        });
    }
}
