package com.crm.model;

import java.io.InputStream;

import com.crm.dao.DatabaseEntity;


public class Image implements DatabaseEntity{

    private int imageId;
    private String fileName;
    private InputStream fileContent;

    @Override
    public Object[] toObjectArray(){
        return new Object[] { imageId, fileName,fileContent };
    }

}
