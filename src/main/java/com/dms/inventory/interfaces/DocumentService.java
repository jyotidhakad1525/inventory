package com.dms.inventory.interfaces;

import com.dms.inventory.model.DocumentUploadDto;

public interface DocumentService {
    DocumentUploadDto saveDocuments(DocumentUploadDto document);
}
