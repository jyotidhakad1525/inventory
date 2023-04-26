package com.dms.inventory.requestImpl;

import com.dms.inventory.aws.utils.AWSS3Service;
import com.dms.inventory.interfaces.DocumentService;
import com.dms.inventory.model.DocumentUploadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final AWSS3Service awss3Service;
    @Value("${document-path}")
    private String path;

    public DocumentServiceImpl(AWSS3Service awss3Service) {
        this.awss3Service = awss3Service;
    }

    public DocumentUploadDto saveDocuments(DocumentUploadDto document) {
        DocumentUploadDto documentUploadDto = new DocumentUploadDto();
        try {
            documentUploadDto.setDocumentType(document.getDocumentType());
            if (document.getFile().getInputStream().available() > 0) {
                String fileName = awss3Service.uploadFile(document.getFile(), document.getDocumentType(),
                        document.getModel());
                documentUploadDto.setDocumentPath(path + fileName + document.getFile().getOriginalFilename());
                documentUploadDto.setFileName(document.getFile().getOriginalFilename());
                documentUploadDto.setKeyName(fileName.concat(document.getFile().getOriginalFilename()));
            }
            return documentUploadDto;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return documentUploadDto;
    }

}
