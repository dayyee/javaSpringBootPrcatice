package com.example.management.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class AwsSnsService {
    private final SnsClient snsClient;

    @Value("${aws.sns.topic.arn}")
    private String snsTopicArn;

    // config에서 주입 가능.
    public AwsSnsService(SnsClient snsClient){
        this.snsClient= snsClient;
    }

    // SNS 주제에 메시지를 발행하여 이메일 전송
    public void sendNotification(String message){
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(snsTopicArn)
                .message(message)
                .build();

        snsClient.publish(publishRequest);
        System.out.println("Notifictaion sent to SNS with Message" + message);
    }
}
