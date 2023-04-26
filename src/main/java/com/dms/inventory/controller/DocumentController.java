package com.dms.inventory.controller;

import com.dms.inventory.interfaces.DocumentService;
import com.dms.inventory.model.DocumentUploadDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping(value = "uploaddocument", method = RequestMethod.POST, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadDocument(@ModelAttribute DocumentUploadDto document) {
        DocumentUploadDto dto = documentService.saveDocuments(document);
        if (Objects.nonNull(dto)) {
            return new ResponseEntity<DocumentUploadDto>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to Upload document to s3 bucket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
