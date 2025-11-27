package com.board.api.service;

import com.board.api.dto.FileDto;
import com.board.api.util.AmazonS3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3Util amazonS3Util;

    public FileDto uploadFile()

}
