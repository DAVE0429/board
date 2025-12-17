package com.board.api.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class S3FileClient {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;


    public String uploadFile(String dir, String storedName, File file){
        String key = dir + "/" + storedName;
        amazonS3.putObject(new PutObjectRequest(bucket, key, file));

        return amazonS3.getUrl(bucket, key).toString();
    }

    public void deleteFile(String dir, String storedName) {

        if (storedName == null || storedName.isBlank()) {
            throw new IllegalArgumentException("storedName 없음");
        }

        String key = dir + "/" + storedName;
        amazonS3.deleteObject(bucket, key);

    }

}
