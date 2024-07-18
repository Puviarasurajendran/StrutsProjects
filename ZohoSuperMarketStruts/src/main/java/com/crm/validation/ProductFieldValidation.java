package com.crm.validation;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm.model.Product;

public class ProductFieldValidation{
    private  HttpServletResponse response = ServletActionContext.getResponse();

    public boolean validateProduct(Product p) throws CustomValidateException {

        LinkedHashMap<String, String> details= new LinkedHashMap<>();
        boolean flag = true;
        if(p.getProductName()==null ) {
            flag = false;
            details.put("key", "ProductName");
            details.put("Resolution", "This key Mandatory & can't accept null value ");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           throw new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND,details,"error","Please check the Details to resolve");
        }
        else if(p.getProductName().length()>20) {
               flag = false;
               details.put("key", "ProductName");
               details.put("Resolution", "This key length must be within 20 character");
              response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          throw new CustomValidateException(ResponseCode.INVALID_DATA,details,"error", "Please check the Details to resolve");
        }
        else if(p.getPrice()<=0 ) {
               details.put("key", "price");
               details.put("Resolution", "This key Mandatory & can't accept null or less than zero ");
               flag = false;
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               throw new CustomValidateException(p.getPrice()==0?ResponseCode.MANDATORY_NOT_FOUND:ResponseCode.INVALID_DATA,details,"error", "Please check the Details to resolve ");
            }
        else if (p.getStock()<0) {
            details.put("key", "stock");
            details.put("Resolution", "This key Mandatory & can't accept null or less than zero ");
               flag = false;
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
              throw new CustomValidateException(p.getStock()==0?ResponseCode.MANDATORY_NOT_FOUND:ResponseCode.INVALID_DATA,details, "error", "Please check the Details to resolve ");
        }

        return flag;

    }

}
