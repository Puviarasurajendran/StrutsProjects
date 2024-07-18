package com.crm.validation;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm.model.Customer;

public class FeildValidationUtils{

    private HttpServletResponse response = ServletActionContext.getResponse();

    public boolean validationCustomer(Customer c) throws CustomValidateException{

        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        boolean flag = true;
        if(c.getFirstName() == null){

            flag = false;
            details.put("key", "FirstName");
            details.put("Resolution", "This key Mandatory & can't accept null value ");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND, details, "error",
                                            "Please check the Details to resolve");

        } else if(c.getFirstName().length() > 20){
            flag = false;
            details.put("key", "FirstName");
            details.put("Resolution", "This key length must be within 20 character");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.INVALID_DATA, details, "error",
                                            "Please check the Details to resolve");
        } else if(c.getEmail()== null){
            details.put("key", "email");
            details.put("Resolution", "This key Mandatory & can't accept null value ");
            flag = false;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND, details, "error",
                                            "Please check the Details to resolve ");
        } else if(!isValidEmail(c.getEmail())){
            details.put("key", "email");
            details.put("Resolution", "This key value doesn't exits email format");
            flag = false;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.INVALID_DATA, details, "error",
                                            "Please check the Details to resolve ");
        } else if(c.getEmail().length() > 20){
            details.put("key", "email");
            details.put("Resolution", "This key length must be within 20 character");
            flag = false;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.INVALID_DATA, details, "error",
                                            "Please check the Details to resolve ");
        }
        else if(c.getPhone()== null){
            details.put("key", "phone");
            details.put("Resolution", "This key Mandatory & can't accept null value ");
            flag = false;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND, details, "error",
                                            "Please check the Details to resolve ");
        }
        else if(!isValidPhoneNumber(c.getPhone())){
            details.put("key", "phone");
            details.put("Resolution", " please enter valid phone number");
            flag = false;
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new CustomValidateException(ResponseCode.INVALID_DATA, details, "error",
                                            "Incorrect phone number ");
        }
        return flag;

    }

    private static final String EMAIL_PATTERN =
                                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                                                                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static final String PHONE_NUMBER_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        return pattern.matcher(phoneNumber).matches();
    }
}
