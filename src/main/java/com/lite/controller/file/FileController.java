package com.lite.controller.file;

import com.lite.dto.ResponseResult;
import com.lite.service.File.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    FileService service;
    /**
     * 文件上传接口
     */
    @PostMapping("/upload")
    public ResponseResult<String> upload(@RequestParam("file")MultipartFile file,@RequestParam("user") String userName){
        return service.uploadFile(file,userName);
    }

    /**
     * 文件下载接口
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName){
        return service.downloadFile(fileName);
    }

    @GetMapping("/getOriginalName")
    public ResponseResult<String> getOriginalName(@RequestParam String newFileName){
        return service.getOriginalName(newFileName);
    }
}
