package com.board.api.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonS3Util {

    @Value("${aws.bucket}")
    private String bucket;

    private final AmazonS3 s3;

    private final AmazonS3Client amazonS3Client;


    public String uploadFile(String folderName, File file){
        String fileName = folderName + "/" + UUID.randomUUID() + "_" + file.getName().replaceAll("\\s", "_");
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();

    }

}
