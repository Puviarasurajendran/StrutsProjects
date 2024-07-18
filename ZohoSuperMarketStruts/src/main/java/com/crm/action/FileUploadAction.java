package com.crm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm.dao.DAOManager;
import com.crm.validation.CustomValidateException;
import com.crm.validation.ResponseCode;
import com.opensymphony.xwork2.ActionSupport;

import jsonbodyutils.JsonBodyUtils;

public class FileUploadAction extends ActionSupport{

    private File file;
    private String fileContentType;
    private String fileFileName;

    JsonBodyUtils jsonUtil = new JsonBodyUtils();

    private int imageId;
    private InputStream inputStream;
    private String fileName;

    private DAOManager fileUploadDAO = new DAOManager();
    HttpServletResponse response = ServletActionContext.getResponse();

    private String res;

    @Override
    public String execute() throws CustomValidateException{
        System.out.println("enter inside a execute method");
        if(file == null){
            System.out.println("enter inside execute method FileUploadAction file null");
            res = "Please select a file to upload";
            return ERROR;
        }

        try (InputStream inputStream = new FileInputStream(file)){
            String fileFullName = file.getName();
            System.out.println("File name :" + fileFullName);
            if(validateImage(fileFullName)){
                System.out.println("File name :" + fileFullName);
                boolean isUploaded = fileUploadDAO.saveImageFile(fileFullName, inputStream);
                if(isUploaded){
                    System.out.println("enter inside execute method FileUploadAction isUploaded");
                    res = "File uploaded and saved into database successfully: ";
                    LinkedHashMap<String, String> details = new LinkedHashMap<>();
                    details.put("key", "Image");
                    details.put("Resolution", "Image file uploaded successfully ");
                    CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "Success",
                                                    res);
                    jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
                    return SUCCESS;
                } else{
                    System.out.println("enter inside execute method FileUploadAction isUploaded else");
                    res = "Failed to upload file";
                    return ERROR;
                }
            }
        } catch(Exception e){
            System.out.println("enter inside execute method FileUploadAction exception");
            LinkedHashMap<String, String> details = new LinkedHashMap<>();
            res = "Error uploading file: " + e.getMessage();
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.INVALID_DATA, details, "error",
                                            "Please check the Details to resolve");
        }
        return ERROR;
    }

    public String getImage() throws CustomValidateException{
        System.out.println("Eneter inside getImage imageId :" + imageId);
        try{
            Blob imageBlob = fileUploadDAO.getFileById(imageId);
            if(imageBlob != null){
                System.out.println("Eneter inside getImage imageBlob :" + imageBlob);
                inputStream = imageBlob.getBinaryStream();
                fileName = fileUploadDAO.getFileNameById(imageId);
                return SUCCESS;
            } else{
                res = "Image not found";
                return ERROR;
            }
        } catch(Exception e){
            res = "Error retrieving image: " + e.getMessage();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            CustomValidateException ex = new CustomValidateException(ResponseCode.INVALID_DATA, null, "error",
                                            "Please check the Details to resolve");
            jsonUtil.writeResponseMessage(response, (HashMap) ex.toMap());
            return ERROR;
        }
    }

    public File getFile(){
        return file;
    }

    public void setFile(File file){
        this.file = file;
    }

    public String getFileContentType(){
        return fileContentType;
    }

    public void setFileContentType(String fileContentType){
        this.fileContentType = fileContentType;
    }

    public String getFileFileName(){
        return fileFileName;
    }

    public void setFileFileName(String fileFileName){
        this.fileFileName = fileFileName;
    }

    public String getRes(){
        return res;
    }

    public void setRes(String res){
        this.res = res;
    }

    public int getImageId(){
        return imageId;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public InputStream getInputStream(){
        return inputStream;
    }

    public void setInputStream(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public boolean validateImage(String fileFullName) throws CustomValidateException{

        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        boolean flag = true;
        if(fileFullName.length() > 99){
            flag = false;
            details.put("key", "ImageName");
            details.put("Resolution", "This key length must be within 99 character");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            CustomValidateException e = new CustomValidateException(ResponseCode.INVALID_DATA, details, "error",
                                            "Please check the Details to resolve");
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            return false;
        }
        return flag;

    }

}
