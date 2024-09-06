package com.example.management.aws;

import com.example.management.model.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AwsSqsService {

    private final SqsClient sqsClient;
    private final AwsSnsService awsSnsService;

    public AwsSqsService(SqsClient sqsClient, AwsSnsService awsSnsService){
        this.sqsClient= sqsClient;
        this.awsSnsService = awsSnsService;
    }
// @Value("${aws.sqs.fifo.queue.url}")
    @Value("${aws.sqs.queue.url}")
    private String queueUrl;


    // 1. SQS 메시지 전송 메서드
    public void sendMessage(UserDTO userDTO) {

        // SQS에 메시지 전송 요청 생성
        String name = userDTO.getName();
        Integer id = userDTO.getId();

        String messageBody = "회원정보 삭제 알람 : id는 "+ id + "인 " + name + "의 정보가 삭제되었습니다.";
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)  // SQS 대기열 URL
                .messageBody(messageBody)  // 메시지 내용
                .build();
        // 메시지 전송
        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Message sent to SQS: " + messageBody);

        receiveAndSendToSNS();
    }


    @Async
    // 2. SQS에서 메시지를 수신한 후 SNS로 발행
    public CompletableFuture<Void> receiveAndSendToSNS() {
        // return CompletableFuture.runAsync(() -> { @Async 써서 안써도됨.
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        if (messages.isEmpty()) {
            System.out.println("No messages to process.");
            return CompletableFuture.completedFuture(null);
        }

        // 순차적으로 처리, 아래와 같은 코드
//            for (Message message : messages) {
//                String messageBody = message.body();
        // 각 메시지를 비동기적으로 순차 처리

        // ObjectMapper를 이용하여 메시지 본문을 파싱
        // ObjectMapper objectMapper = new ObjectMapper();

        messages.forEach(message -> {
            try {
                // Jackson이라고하는 라이브러리의 핵심 클래스(ObjectMapper)
//                // readTree() 메서드를 사용해, JSON 데이터를 JsonNode 객체로 변환합니다.
//                JsonNode jsonNode = objectMapper.readTree(message.body());
//                // SQS 메시지의 본문({"message": "~~"})에서 "message" 키 값 추출
//                String messageBody = jsonNode.get("message").asText();

                String messageBody = message.body();
                // SNS로 메시지 전송 (추출한 메시지 내용만 전송)
                awsSnsService.sendNotification(messageBody)
                        .thenRun(() -> {
                            // SNS 발행 후 메시지를 SQS에서 삭제
                            deleteMessage(message);
                        });

            } catch (Exception e) {
                System.out.println("Error parsing message: " + e.getMessage());
            }
        });
        return CompletableFuture.completedFuture(null);
    }

    // 3. 수신한 메시지 삭제
    private void deleteMessage(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
        System.out.println("Message deleted from SQS: " + message.body());
    }
}