package com.dms.inventory.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class DocumentUploadDto {

    private MultipartFile file;
    private String fileName;
    private String documentType;
    private String documentPath;
    private String keyName;
    private String model;

}