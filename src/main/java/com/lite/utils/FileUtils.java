package com.lite.utils;

import org.springframework.util.ClassUtils;

public class FileUtils {

    public static final String RAW ="RAW";

    public static final String IMG = "IMG";

    public static final String FILE = "FILE";
    public static String getUploadFileDir(){
        return ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/file/";
    }
}
