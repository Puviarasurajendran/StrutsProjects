package com.crm.validation;

import java.util.HashMap;
import java.util.Map;

public class CustomValidateException extends Exception{

    private static final long serialVersionUID = 1L;
    private ResponseCode code;
    private Object details;
    private String status;
    private String message;

    public CustomValidateException(ResponseCode code, Object details, String status, String message){
        this.code = code;
        this.details = details;
        this.status = status;
        this.message = message;
    }

    public ResponseCode getCode(){
        return code;
    }

    public void setCode(ResponseCode code){
        this.code = code;
    }

    public Object getDetails(){
        return details;
    }

    public void setDetails(Object details){
        this.details = details;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return "{code :" + code + ", details:" + details + ", status:" + status + ", message:"
                                        + message + "}";
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("details", details);
        map.put("status", status);
        map.put("message", message);
        return map;
    }

}
