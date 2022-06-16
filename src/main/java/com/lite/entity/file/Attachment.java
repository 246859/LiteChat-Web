package com.lite.entity.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    Integer eid;

    String uploader;

    String fileName;

    String originalName;

    String path;
}
