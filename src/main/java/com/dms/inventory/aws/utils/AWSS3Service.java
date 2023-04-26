package com.dms.inventory.aws.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.dms.inventory.exception.CustomeServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * @author saritha created on 21/02/20
 */
@Component
public class AWSS3Service {


    @Value("${bucketName}")
    private String bucketName;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${document-path}")
    private String path;

    private AmazonS3 s3client;

    private String filename;

    public String getFilename() {
        return filename;
    }

    @SuppressWarnings("deprecation")
    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }


    private String createFolder(String customerId, String type) {
        try {
            String key = customerId.concat("/");
            //TODO : MNeeds fix
            InputStream input = new ByteArrayInputStream(new byte[0]);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            s3client.putObject(new PutObjectRequest(bucketName, key, input, metadata));
            return key;
        } catch (Exception e) {
            throw new CustomeServiceException("Unable to create Folder in S3 bucket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String uploadFile(MultipartFile multipartFile, String type, String customerId) {
        String key = createFolder(customerId, type);
        String fileName = null;
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileName = multipartFile.getOriginalFilename();
            uploadFileTos3bucket(key, fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public String uploadBooleanArrayFile(Boolean[] filearray, String type, String customerId) {
        String key = createFolder(customerId, type);
        try {
            File file = convertBooleanArrayToFile(type, filearray);
            filename = file.getName();
            // if we want upload in folder as String fileName = folderName + File.seperator + "testvideo.mp4";
            uploadFileTos3bucket(key, filename, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        //TODO : NEeds fix
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    private void uploadFileTos3bucket(String key, String fileName, File file) {
        try {
            PutObjectResult putObjectResult = s3client.putObject(
                    new PutObjectRequest(bucketName, key + fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new CustomeServiceException("Unable to upload file to S3", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            return true;
        } catch (Exception e) {
            throw new CustomeServiceException("Unable to delete file from S3", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private File convertBooleanArrayToFile(String filename, Boolean[] Booleans) throws IOException {
        File convFile = new File(filename);
        FileOutputStream fos = new FileOutputStream(convFile);
        //fos.write(Booleans);
        fos.close();
        return convFile;
    }

    private File convertbyteArrayToFile(String filename, byte[] bytes) throws IOException {
        File convFile = new File(filename);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(bytes);
        fos.close();
        return convFile;
    }
}
