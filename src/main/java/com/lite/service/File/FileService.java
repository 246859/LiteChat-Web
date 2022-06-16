package com.lite.service.File;

import com.lite.dto.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ResponseResult<String> uploadFile(MultipartFile file,String userName);

    ResponseEntity<byte[]> downloadFile(String fileName);

    ResponseResult<String> getOriginalName(String newFileName);
}
