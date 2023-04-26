package com.dms.inventory.model;

import java.util.List;

import lombok.Data;

@Data
public class BulkUploadResponse {
	private int TotalCount;
    private int SuccessCount;
    private int FailedCount;
    private List<String> FailedRecords;
}
