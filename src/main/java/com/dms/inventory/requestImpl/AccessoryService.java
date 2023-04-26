package com.dms.inventory.requestImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.dms.inventory.aws.utils.AwsCredential;
import com.dms.inventory.entities.Accessory;
import com.dms.inventory.entities.AccessoryMapping;
import com.dms.inventory.enums.Category;
import com.dms.inventory.exception.VehcileAccessorieException;
import com.dms.inventory.mapper.AccessoryMappingDto;
import com.dms.inventory.model.BulkUploadAccessories;
import com.dms.inventory.model.BulkUploadResponse;
import com.dms.inventory.repository.AccessoryMappingRepository;
import com.dms.inventory.repository.AccessoryRepository;
import com.dms.inventory.repository.VehicleDetailRepository;
import com.dms.inventory.response.AccessoryResponseModel;
@Service
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final AccessoryMappingRepository accessoryMappingRepository;
    private final AwsCredential awscred;
	private VehicleDetailRepository vehicleDetailRepositor;

    public AccessoryService(AccessoryRepository accessoryRepository,
                            AccessoryMappingRepository accessoryMappingRepository, AwsCredential awscred,VehicleDetailRepository vehicleDetailRepositor) {
        this.accessoryRepository = accessoryRepository;
        this.accessoryMappingRepository = accessoryMappingRepository;
        this.awscred = awscred;
        this.vehicleDetailRepositor=vehicleDetailRepositor;
    }

    public AccessoryResponseModel getAllAccessoriesByVehicleId(Integer vehicleId, Integer orgId, Integer pageNo,
                                                               Integer pageSize) {
        AccessoryResponseModel responseModel = null;

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Accessory> accessoryList = accessoryRepository.findAllByVehicleIdAndOriganistionId(vehicleId,
                new Long(orgId));
        // for common category vehicleId will be -1
        for (Accessory acc : accessoryRepository.findAllByCategoryAndOriganistionId(Category.Common.name(),
                new Long(orgId))) {
            accessoryList.add(acc);
        }
        Collections.sort(accessoryList, new Comparator<Accessory>() {
            @Override
            public int compare(Accessory o1, Accessory o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });
        responseModel = generateResponseObject("Success", true, false);
        responseModel.setAccessorylist(accessoryList);

        List<AccessoryMapping> accessoryMapping = accessoryMappingRepository
                .findAllByVehicleIdAndOrganisationId(vehicleId, orgId);
        // response.setAccessory(resp);
        List<AccessoryMappingDto> accessoryMappingDto = new ArrayList<AccessoryMappingDto>();
        for (AccessoryMapping accessoryMappingobj : accessoryMapping) {
            AccessoryMappingDto accessoryMappingDtoObj = new AccessoryMappingDto();
            accessoryMappingDtoObj.setKitName(accessoryMappingobj.getKitName());
            accessoryMappingDtoObj.setId(accessoryMappingobj.getId());
            accessoryMappingDtoObj.setOrganisationId(accessoryMappingobj.getOrganisationId());
            accessoryMappingDtoObj.setVehicleId(accessoryMappingobj.getVehicleId());
            accessoryMappingDtoObj.setCost(accessoryMappingobj.getCost());
            String accessoryListStr = accessoryMappingobj.getAccessoriesList();
            // String[] strList = {};
            if (accessoryListStr != null && !accessoryListStr.isEmpty()) {
                String[] strList = accessoryListStr.split("\\|");
                List<Integer> vehicleIdSet = new ArrayList<Integer>();
                for (String str : strList) {
                    vehicleIdSet.add(Integer.parseInt(str));
                }
                List<Accessory> accessorylist = accessoryRepository.findAccessoryByIdList(vehicleIdSet);
                if (Objects.nonNull(accessorylist)) {
                    Collections.sort(accessorylist, new Comparator<Accessory>() {
                        @Override
                        public int compare(Accessory o1, Accessory o2) {
                            return o2.getId().compareTo(o1.getId());
                        }
                    });
                }
                accessoryMappingDtoObj.setAccessory(accessorylist);
            }
            accessoryMappingDto.add(accessoryMappingDtoObj);

        }
        if (Objects.nonNull(accessoryMappingDto)) {
            Collections.sort(accessoryMappingDto, new Comparator<AccessoryMappingDto>() {
                @Override
                public int compare(AccessoryMappingDto o1, AccessoryMappingDto o2) {
                    return Integer.valueOf(o2.getId()).compareTo(Integer.valueOf(o1.getId()));
                }
            });
        }
        responseModel.setAccessoryMappingDto(accessoryMappingDto);
        responseModel.setAccessory(null);
        return responseModel;
    }

    public List<Accessory> getAllAccessories(int pageNo, int pageSize, int ordId) {
        List<Accessory> accessories = new ArrayList<>();
        Pageable firstPageWithTwoElements = PageRequest.of(pageNo, pageSize);
        accessoryRepository.findAllByOriganistionId(new Long(ordId)).forEach(accessories::add);
        Collections.sort(accessories, new Comparator<Accessory>() {
            @Override
            public int compare(Accessory o1, Accessory o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });
        return accessories;
    }

    public List<Accessory> getAllAccessoriesId(Integer Id, int ordId) {
        return accessoryRepository.findAllByIdAndOriganistionId(Id, new Long(ordId));
    }

    public Accessory saveAllAccessories(Accessory accessory) {
        System.out.println(accessory);
        Accessory accObj = accessoryRepository.save(accessory);
        // accObj.setImageUrl(uploadFileS3(file,generateFileName(file,accObj.getId().toString())));
        System.out.println(accObj);
        //return accessoryRepository.save(accObj);
        return accObj;
    }

    public Accessory updateAccessories(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    private String uploadFileS3(MultipartFile file, String filename) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(awscred.getAccessKey(), awscred.getSecretKey());
        Regions clientRegion = Regions.fromName("ap-south-1");
        String bucketName = "inventoryaccessories";
        String folderName = "Accessories";
        String uri = "";
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(clientRegion).build();
            TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3Client)
                    .withMultipartUploadThreshold((long) (5 * 1024 * 1025)).build();
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            String fileUrl = "https://inventoryaccessories.s3.ap-south-1.amazonaws.com/" + "" + folderName + "/"
                    + filename;
            ProgressListener progressListener = progressEvent -> System.out
                    .println("Transferred bytes: " + progressEvent.getBytesTransferred());
            PutObjectRequest request = new PutObjectRequest(bucketName, filename, convFile);
            request.setGeneralProgressListener(progressListener);
            tm.upload(request);
            uri = fileUrl;
            return uri;
        } catch (AmazonServiceException e) {
            return uri;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return uri;
        }

    }

    private String generateFileName(MultipartFile multiPart, String filename) {
        return filename + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public AccessoryResponseModel deleteAccessoriesById(Integer id) {
        AccessoryResponseModel responseModel = null;
        Optional<Accessory> optional = accessoryRepository.findById(id);
        if (optional.isPresent()) {
            List<AccessoryMapping> accessoryMapping = accessoryMappingRepository.findAllByVehicleIdAndOrganisationId(
                    optional.get().getVehicleId(), optional.get().getOriganistionId().intValue());
            if (!CollectionUtils.isEmpty(accessoryMapping)) {
                for (AccessoryMapping accessoryMapping1 : accessoryMapping) {
                    String[] strList = accessoryMapping1.getAccessoriesList().split("\\|");
                    List<Integer> accessoriesId = new ArrayList<Integer>();
                    for (String str : strList) {
                        accessoriesId.add(Integer.parseInt(str));
                    }
                    if (accessoriesId.contains(optional.get().getId())) {
                        responseModel = generateResponseObject(
                                "Their is a Kit associated with this accessory please delete kit to delete accessory",
                                false, true);
                        // throw new CustomeServiceException("Their is a Kit associated with this
                        // accessory please delete kit to delete accessory ", HttpStatus.BAD_REQUEST);
                    }
                }
                accessoryRepository.delete(optional.get());
                responseModel = generateResponseObject("Successfully deleted", true, false);
            } else {
                accessoryRepository.delete(optional.get());
                responseModel = generateResponseObject("Successfully deleted", true, false);
            }
        } else {
            responseModel = generateResponseObject("No accessory found with provided Id" + id, false, true);
            // throw new CustomeServiceException("No accessory found with provided Id " +
            // id, HttpStatus.BAD_REQUEST);
        }

        return responseModel;
    }

    private AccessoryResponseModel generateResponseObject(String message, boolean success, boolean error) {
        AccessoryResponseModel respModel = new AccessoryResponseModel();
        if (success) {
            respModel.setSuccess(success);
            respModel.setSuccessMessage(message);
        } else {
            respModel.setError(error);
            respModel.setAccessory(null);
            respModel.setAccessorylist(null);
            respModel.setAccessoryMappingDto(null);
            respModel.setErrorMessage(message);
        }
        return respModel;
    }

    public AccessoryResponseModel updateAccessory(Accessory accessory) {
        AccessoryResponseModel respModel = null;
        Accessory accObj = accessoryRepository.findById(accessory.getId()).get();
        if (Objects.nonNull(accObj)) {
            accObj.setCategory(accessory.getCategory());
            accObj.setCost(accessory.getCost());
            accObj.setCreatedBy(accessory.getCreatedBy());
            accObj.setCreatedDate(accessory.getCreatedDate());
            accObj.setImageUrl(accessory.getImageUrl());
            accObj.setItem(accessory.getItem());
            accObj.setModifiedBy(accessory.getModifiedBy());
            accObj.setModifiedDate(accessory.getModifiedDate());
            accObj.setOriganistionId(accessory.getOriganistionId());
            accObj.setPartName(accessory.getPartName());
            accObj.setPartNo(accessory.getPartNo());
            accObj.setVehicleId(accessory.getVehicleId());
            accessoryRepository.save(accObj);

            respModel = generateResponseObject("Updated Successfully", true, false);

        } else {
            respModel = generateResponseObject("Requested accessory ID not found", false, true);
        }
        return respModel;
    }
    
	public BulkUploadResponse processBulkExcelForAccessory2(String inputFilePath,
			BulkUploadAccessories bulkUploadReq) throws Exception {

		Workbook workbook = null;
		Sheet sheet = null;
		List<Accessory> accessoryList = new ArrayList<>();
		workbook = getWorkBook(new File(inputFilePath));
		sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		List<String> FailedRecords =new ArrayList<>();
		int TotalCount =-1;
		int SuccessCount=0;
		int FailedCount=0;
		int emptyCheck=0;
		BulkUploadResponse res = new BulkUploadResponse();
		while (rowIterator.hasNext()) {
			TotalCount++;
			Row row = rowIterator.next();
			try {
				if (row.getRowNum() != 0) {
					emptyCheck++;
					Accessory accessory = new Accessory();
					if (bulkUploadReq.getVehicleId()!=null) {
						accessory.setVehicleId(bulkUploadReq.getVehicleId());
					} else {
						throw new VehcileAccessorieException("VehicleId not present");
					}
					if (bulkUploadReq.getOriganistionId()!=null) {
						accessory.setOriganistionId(bulkUploadReq.getOriganistionId());
					} else {
						throw new VehcileAccessorieException("OriganistionId not present");
					}
					if (bulkUploadReq.getEmpId()!=null) {
						accessory.setCreatedBy(bulkUploadReq.getEmpId());
					} else {
						throw new VehcileAccessorieException("EmpId not present");
					}
					if (bulkUploadReq.getEmpId()!=null) {
						accessory.setModifiedBy(bulkUploadReq.getEmpId());
					}
					accessory.setCreatedDate(Timestamp.from(Instant.now()));
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 1))) {
						if (getCellValueBasedOnCellType(row, 1).equals("Exterior")
								|| getCellValueBasedOnCellType(row, 1).equals("exterior")) {
							accessory.setCategory(Category.Exterior);
						}
						if (getCellValueBasedOnCellType(row, 1).equals("Interior")
								|| getCellValueBasedOnCellType(row, 1).equals("interior")) {
							accessory.setCategory(Category.Interior);
						}
						if (getCellValueBasedOnCellType(row, 1).equals("Common")
								|| getCellValueBasedOnCellType(row, 1).equals("common")) {
							accessory.setCategory(Category.Common);
						}
						if (getCellValueBasedOnCellType(row, 1).equals("Kit")
								|| getCellValueBasedOnCellType(row, 1).equals("kit")) {
							accessory.setCategory(Category.Kit);
						}
					} else {
						throw new VehcileAccessorieException("Category type Must be in : Exterior,Interior,Common,Kit");
					}
					accessory.setImageUrl("Not Available");
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 2))) {
						try {
							accessory.setPartName(getCellValueBasedOnCellType(row, 2));
						} catch (IllegalArgumentException ex) {
							throw new VehcileAccessorieException("PartName field cannot be blank");
						}
					} else {
						throw new VehcileAccessorieException("PartName field cannot be blank");
					}
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 3))) {
						try {
							accessory.setPartNo(getCellValueBasedOnCellType(row, 3));
						} catch (IllegalArgumentException ex) {
							throw new VehcileAccessorieException("PartNo field cannot be blank");
						}
					} else {
						throw new VehcileAccessorieException("PartNo field cannot be blank");
					}
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 4))) {
						if (getCellValueBasedOnCellType(row, 4).equals("paid")
								|| getCellValueBasedOnCellType(row, 4).equals("Paid")) {
							accessory.setItem("MRP");
						} else {
							accessory.setItem(getCellValueBasedOnCellType(row, 4));
						}
					} else {
						throw new VehcileAccessorieException("Type must be in :MRP,FOC,Others");
					}
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 5))) {
						accessory.setCost(new BigDecimal(getCellValueBasedOnCellType(row, 5)));
					}
					accessoryList.add(accessory);
				}	
			}catch(Exception e) {
				String resonForFailure = e.getMessage();
				System.out.println(resonForFailure);
				FailedRecords.add(resonForFailure);
				continue;	
		}
		}
		if(emptyCheck==0) {
			String resonForFailure = "DATA NOT FOUND";
			System.out.println(resonForFailure);
			FailedRecords.add(resonForFailure);
		}
		
		int j=0;
		AccessoryResponseModel respModel = null;
		for (Accessory accessory : accessoryList) {
			try {
				j++;
			Accessory accessorydata = saveAllAccessories(accessory);
			respModel = generateResponseObject("Accessory successfully created", true, false);
			respModel.setAccessory(accessorydata);
			respModel.setAccessorylist(null);
			respModel.setAccessoryMappingDto(null);
			SuccessCount++;
		}catch(DataAccessException e) {
			String resonForFailure = "DUPLICATE ENTRY IN "+j+" ROW FOUND";
			System.out.println(resonForFailure);
			FailedRecords.add(resonForFailure);
			continue;
		}catch(Exception e) {
			String resonForFailure = "ERROR IN SAVEING DATA FOR "+j+" ROW "+e.getMessage();
			System.out.println(resonForFailure);
			FailedRecords.add(resonForFailure);
			continue;
		}	
	}
	FailedCount=TotalCount-SuccessCount;
	res.setFailedCount(FailedCount);
	res.setFailedRecords(FailedRecords);
	res.setSuccessCount(SuccessCount);
	res.setTotalCount(TotalCount);
	return res;
}
	
	
	
	public  BulkUploadResponse processBulkExcelForAccesory2(BulkUploadAccessories bulkUploadReq,MultipartFile bulkExcel)
			throws Exception {
	    Resource file = null;
		if (bulkExcel.isEmpty()) {
			BulkUploadResponse res = new BulkUploadResponse();
			List<String> FailedRecords =new ArrayList<>();
			String resonForFailure = "File not found";
			FailedRecords.add(resonForFailure);
			res.setFailedCount(0);
			res.setFailedRecords(FailedRecords);
			res.setSuccessCount(0);
			res.setTotalCount(0);
			return res;
		}
			Path tmpDir = Files.createTempDirectory("temp");
			Path tempFilePath = tmpDir.resolve(bulkExcel.getOriginalFilename());
			Files.write(tempFilePath, bulkExcel.getBytes());
			String fileName = bulkExcel.getOriginalFilename();
			fileName = fileName.substring(0, fileName.indexOf("."));
			return processBulkExcelForAccessory2(tempFilePath.toString(), bulkUploadReq);
	}
	
	private Workbook getWorkBook(File fileName)
	{
		Workbook workbook = null;
		try {
			String myFileName=fileName.getName();
			String extension = myFileName.substring(myFileName.lastIndexOf("."));
			if(extension.equalsIgnoreCase(".xls")){
				workbook = new HSSFWorkbook(new FileInputStream(fileName));
			}
			else if(extension.equalsIgnoreCase(".xlsx")){
				workbook = new XSSFWorkbook(new FileInputStream(fileName));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return workbook;
	}
    
	private String getCellValueBasedOnCellType(Row rowData,int columnPosition)
	{
		String cellValue=null;
		Cell cell = rowData.getCell(columnPosition);
		if(cell!=null){
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			{
				String inputCellValue=cell.getStringCellValue();
				if(inputCellValue.endsWith(".0")){
					inputCellValue=inputCellValue.substring(0, inputCellValue.length()-2);
				}
				cellValue=inputCellValue;
			}
			else if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
			{
				if(DateUtil.isCellDateFormatted(cell)) {
					 
					 DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    
					    Date today = cell.getDateCellValue() ;      
					   
					    cellValue = df.format(today);
				        		
				    }else {
				Integer doubleVal = (int) cell.getNumericCellValue();
				cellValue= Integer.toString(doubleVal);
				    }
			}
			
		}
		return cellValue;
	}
	
	public  BulkUploadResponse processBulkExcelForAccesory(BulkUploadAccessories bulkUploadReq,MultipartFile bulkExcel)
			throws Exception {
	    Resource file = null;
		if (bulkExcel.isEmpty()) {
			BulkUploadResponse res = new BulkUploadResponse();
			List<String> FailedRecords =new ArrayList<>();
			String resonForFailure = "File not found";
			FailedRecords.add(resonForFailure);
			res.setFailedCount(0);
			res.setFailedRecords(FailedRecords);
			res.setSuccessCount(0);
			res.setTotalCount(0);
			return res;
		}
			Path tmpDir = Files.createTempDirectory("temp");
			Path tempFilePath = tmpDir.resolve(bulkExcel.getOriginalFilename());
			Files.write(tempFilePath, bulkExcel.getBytes());
			String fileName = bulkExcel.getOriginalFilename();
			fileName = fileName.substring(0, fileName.indexOf("."));
			return processBulkExcelForAccessory(tempFilePath.toString(), bulkUploadReq);
	}
	
	public BulkUploadResponse processBulkExcelForAccessory(String inputFilePath,
			BulkUploadAccessories bulkUploadReq) throws Exception {

		Workbook workbook = null;
		Sheet sheet = null;
		List<Accessory> accessoryList = new ArrayList<>();
		workbook = getWorkBook(new File(inputFilePath));
		sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		List<String> FailedRecords =new ArrayList<>();
		int TotalCount =-1;
		int SuccessCount=0;
		int FailedCount=0;
		int emptyCheck=0;
		BulkUploadResponse res = new BulkUploadResponse();
		while (rowIterator.hasNext()) {
			TotalCount++;
			Row row = rowIterator.next();
			try {
				if (row.getRowNum() != 0) {
					emptyCheck++;
					Accessory accessory = new Accessory();
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 0))) {
						Integer vehicleId = vehicleDetailRepositor.findAllByVehicleIdAndOriganistionId(getCellValueBasedOnCellType(row, 0),bulkUploadReq.getOriganistionId().intValue());
						if(vehicleId==null) {
							throw new VehcileAccessorieException("Model not found");
						}else {
							accessory.setVehicleId(vehicleId);
						}
					} else {
						throw new VehcileAccessorieException("Model not present");
					}
					if (bulkUploadReq.getOriganistionId()!=null) {
						accessory.setOriganistionId(bulkUploadReq.getOriganistionId());
					} else {
						throw new VehcileAccessorieException("OriganistionId not present");
					}
					if (bulkUploadReq.getEmpId()!=null) {
						accessory.setCreatedBy(bulkUploadReq.getEmpId());
					} else {
						throw new VehcileAccessorieException("EmpId not present");
					}
					if (bulkUploadReq.getEmpId()!=null) {
						accessory.setModifiedBy(bulkUploadReq.getEmpId());
					}
					accessory.setCreatedDate(Timestamp.from(Instant.now()));
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 3))) {
						if (getCellValueBasedOnCellType(row, 3).equals("Exterior")
								|| getCellValueBasedOnCellType(row, 3).equals("exterior")) {
							accessory.setCategory(Category.Exterior);
						}
						if (getCellValueBasedOnCellType(row, 3).equals("Interior")
								|| getCellValueBasedOnCellType(row, 3).equals("interior")) {
							accessory.setCategory(Category.Interior);
						}
						if (getCellValueBasedOnCellType(row, 3).equals("Common")
								|| getCellValueBasedOnCellType(row, 3).equals("common")) {
							accessory.setCategory(Category.Common);
						}
						if (getCellValueBasedOnCellType(row, 3).equals("Kit")
								|| getCellValueBasedOnCellType(row, 3).equals("kit")) {
							accessory.setCategory(Category.Kit);
						}
					} else {
						throw new VehcileAccessorieException("Category type Must be in : Exterior,Interior,Common,Kit");
					}
					accessory.setImageUrl("Not Available");
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 1))) {
						try {
							accessory.setPartName(getCellValueBasedOnCellType(row, 1));
						} catch (IllegalArgumentException ex) {
							throw new VehcileAccessorieException("PartName field cannot be blank");
						}
					} else {
						throw new VehcileAccessorieException("PartName field cannot be blank");
					}
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 2))) {
						try {
							accessory.setPartNo(getCellValueBasedOnCellType(row, 2));
						} catch (IllegalArgumentException ex) {
							throw new VehcileAccessorieException("PartNo field cannot be blank");
						}
					} else {
						throw new VehcileAccessorieException("PartNo field cannot be blank");
					}
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 4))) {
						if (getCellValueBasedOnCellType(row, 4).equals("paid")
								|| getCellValueBasedOnCellType(row, 4).equals("Paid")) {
							accessory.setItem("MRP");
						} else {
							accessory.setItem(getCellValueBasedOnCellType(row, 4));
						}
					} else {
						throw new VehcileAccessorieException("Type must be in :MRP,FOC,Others");
					}
					if (StringUtils.isNotBlank(getCellValueBasedOnCellType(row, 5))) {
						accessory.setCost(new BigDecimal(getCellValueBasedOnCellType(row, 5)));
					}
					accessoryList.add(accessory);
				}
			}catch(Exception e) {
				String resonForFailure = e.getMessage();
				System.out.println(resonForFailure);
				FailedRecords.add(resonForFailure);
				continue;
			}
		}
		if(emptyCheck==0) {
			String resonForFailure = "DATA NOT FOUND";
			System.out.println(resonForFailure);
			FailedRecords.add(resonForFailure);
		}
		int j=0;
		AccessoryResponseModel respModel = null;
		for (Accessory accessory : accessoryList) {
			try {
				j++;
			Accessory accessorydata = accessoryRepository.save(accessory);
			respModel = generateResponseObject("Accessory successfully created", true, false);
			respModel.setAccessory(accessorydata);
			respModel.setAccessorylist(null);
			respModel.setAccessoryMappingDto(null);
			SuccessCount++;
		}catch(DataAccessException e) {
			String resonForFailure = "DUPLICATE ENTRY IN "+j+" ROW FOUND";
			System.out.println(resonForFailure);
			FailedRecords.add(resonForFailure);
			continue;
		}catch(Exception e) {
			String resonForFailure = "ERROR IN SAVEING DATA FOR "+j+" ROW "+e.getMessage();
			System.out.println(resonForFailure);
			FailedRecords.add(resonForFailure);
			continue;
		}	
	}
	FailedCount=TotalCount-SuccessCount;
	res.setFailedCount(FailedCount);
	res.setFailedRecords(FailedRecords);
	res.setSuccessCount(SuccessCount);
	res.setTotalCount(TotalCount);
	return res;
}
   
}
