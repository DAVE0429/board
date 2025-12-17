package com.board.api.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${cloud.aws.region}")
    private String region; // application 에서 AWS 지역 값 주입

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey; // AWS IAM 사용자의 Access Key 주입

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey; // AWS IAM 사용자의 Secret Key 주입

    @Bean // Spring Bean으로 등록 -> 다른 곳에서 DI로 사용 가능
    public BasicAWSCredentials basicAWSCredentials(){
        // AWS 인증 키 객체 생성
        return new BasicAWSCredentials(accessKey, secretKey);}

    @Bean // AmazonS3 클라이언트를 Bean으로 등록
    public AmazonS3 s3(){
        // AWS 접근을 위한 인증 객체 생성
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        // S3 클라이언트를 생성하여 반환
        return AmazonS3ClientBuilder
                .standard() // 기본 설정을 가져옴
                .withRegion(region) // AWS S3 리전 설정
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)) // 인증 정보 주입
                .build(); // AmazonS3 Client 반환
    }


}
