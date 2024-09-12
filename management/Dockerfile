# 베이스 이미지로 OpenJDK 사용
FROM openjdk:17

# JAR 파일을 애플리케이션 디렉토리에 복사
COPY build/libs/management-0.0.1-SNAPSHOT.jar /server.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/server.jar"]

# 컨테이너가 사용하는 포트 지정
EXPOSE 8080
