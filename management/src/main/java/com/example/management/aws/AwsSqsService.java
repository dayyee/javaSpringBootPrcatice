package com.example.management.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;

@Service
public class AwsSqsService {

    private final SqsClient sqsClient;
    private final AwsSnsService awsSnsService;

    public AwsSqsService(SqsClient sqsClient, AwsSnsService awsSnsService){
        this.sqsClient= sqsClient;
        this.awsSnsService = awsSnsService;
    }

    @Value("${aws.sqs.queue.url}")
    private String queueUrl;


    // 1. SQS 메시지 전송 메서드
    public void sendMessage(String message) {

        // SQS에 메시지 전송 요청 생성
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)  // SQS 대기열 URL
                .messageBody(message)  // 메시지 내용
                .build();
        // 메시지 전송
        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Message sent to SQS: " + message);
    }



    // 2. SQS에서 메시지를 수신한 후 SNS로 발행
    public void receiveAndSendToSNS() {
        // SQS에서 메시지 수신
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)  // 최대 10개의 메시지를 수신
                .waitTimeSeconds(10)      // 대기 시간 설정
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        if (messages.isEmpty()) {
            System.out.println("No messages to process.");
            return;
        }

        // 수신한 메시지를 SNS로 발행하여 이메일 전송
        for (Message message : messages) {
            String messageBody = message.body();
            awsSnsService.sendNotification(messageBody);  // SNS로 메시지 발행

            System.out.println("Message sent to SNS: " + messageBody);
        }
    }
}
