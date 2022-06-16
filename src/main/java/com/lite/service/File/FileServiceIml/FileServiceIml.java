package com.lite.service.File.FileServiceIml;

import com.lite.dao.chatDao.ChatMapper;
import com.lite.dao.fileDao.FileMapper;
import com.lite.dto.ResponseResult;
import com.lite.entity.file.Attachment;
import com.lite.service.File.FileService;
import com.lite.utils.FileUtils;
import com.lite.utils.LiteHttpExceptionStatus;
import com.lite.utils.ResponseUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceIml implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    ChatMapper chatMapper;
    @Override
    public ResponseResult<String> uploadFile(MultipartFile file,String userName) {

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        File reNameFile;
        String resName;
        String newPath;

        try {
            do {//保证名称唯一
                String uuid = UUID.randomUUID().toString();//生成文件名的唯一标识
                String newFileName = uuid + suffix;
                reNameFile = new File((newPath = FileUtils.getUploadFileDir() + newFileName));
                resName = newFileName;
            } while (reNameFile.exists());

            file.transferTo(reNameFile);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtils.getWrongResponseResult("上传文件失败");
        }

        if (Objects.isNull(chatMapper.getUserInfo(userName))){
            return ResponseUtils.getWrongResponseResult("无效的上传者");
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(resName);
        attachment.setPath(newPath);
        attachment.setOriginalName(fileName);
        attachment.setUploader(userName);

        Integer count = fileMapper.recordAttachment(attachment);

        if (count == 0) {
            return ResponseUtils.getWrongResponseResult("文件记录失败");
        }

        ResponseResult<String> responseResult = new ResponseResult<>();
        responseResult.setMsg("文件上传成功");
        responseResult.setIsSuccess(true);
        responseResult.setData(resName);
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());

        return responseResult;
    }

    /**
     * 将文件读取成二进制文件流返回给前台
     * @param fileName
     * @return
     */
    @Override
    public ResponseEntity<byte[]> downloadFile(String fileName) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            FileInputStream in = new FileInputStream(FileUtils.getUploadFileDir()+fileName);


            byte[] buffer = new byte[1024];

            int count = 0;

            while ((count = in.read(buffer)) !=-1){
                out.write(buffer,0,count);
            }

        } catch (IOException e) {
           e.printStackTrace();
           return null;
        }

        byte[] byteStream =  out.toByteArray();

        //设置响应头
        HttpHeaders httpHeaders = new HttpHeaders();
        //通知浏览器以下载的方式打开文件
        httpHeaders.setContentDispositionFormData("attachment",fileName);
        //定义以流的形式下载返回文件数据
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(byteStream,httpHeaders, HttpStatus.OK);

    }

    @Override
    public ResponseResult<String> getOriginalName(String newFileName) {

        if (Strings.isBlank(newFileName)){
            return ResponseUtils.getWrongResponseResult("无效的文件名");
        }

        String originalName = fileMapper.getOriginalName(newFileName);

        if (Objects.isNull(originalName)){
            return ResponseUtils.getWrongResponseResult("文件不存在");
        }

        ResponseResult<String> responseResult = new ResponseResult<>();
        responseResult.setIsSuccess(true);
        responseResult.setMsg("查询成功");
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setData(originalName);

        return responseResult;
    }
}
