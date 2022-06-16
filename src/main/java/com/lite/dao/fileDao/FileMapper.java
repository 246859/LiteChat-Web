package com.lite.dao.fileDao;

import com.lite.entity.file.Attachment;

public interface FileMapper {
    Integer recordAttachment(Attachment attachment);

    String getOriginalName(String newFileName);
}
