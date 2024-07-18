package com.spring.controller;

import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zoho.util.Messages;
import com.zoho.util.ResponseCode;
import com.zoho.util.ResponseUtils;
import com.zoho.util.StandardResponse;

import proxy.VersionProxyHandlerUtils;
import restdao.CustomersDAO;
import restdao.CustomersInterface;
import restmodel.Customer;
import restvalidation.CustomValidateException;
import restvalidation.ValidationUtils;

@RestController
@RequestMapping("api/{version}/**")
public class CustomerApiController{

    CustomersInterface customersInterface;
    LinkedHashMap details;

    @Autowired
    public CustomerApiController(){
        this.customersInterface = (CustomersInterface) Proxy.newProxyInstance(CustomersInterface.class.getClassLoader(),
                                        new Class[]{ CustomersInterface.class},
                                        new VersionProxyHandlerUtils(new CustomersDAO()));
    }

    @GetMapping("/customers")
    public List<Map<String, Object>> getCustomers(@PathVariable String version){
        System.out.println("inside the GET method call of Spring CustomerApiController version " + version);
        List<Map<String, Object>> customersMap = customersInterface.getTotalCutomers_v1();
        return customersMap;
    }

    @GetMapping("/customers/{id}")
    public Object getCustomerById(@PathVariable("id") int id){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                        .getResponse();
        try{
            Map<String, Object> customersMap = customersInterface.getCustomerByID_v1(id);
            return customersMap;
        } catch(CustomValidateException e){
            response.setStatus(400);
            return ResponseUtils.createErrorResponse(ResponseCode.INTERNAL_ERROR, e.getMessage(),
                                            Messages.RESOURCE_NOT_FOUND);
        }
    }

    @PostMapping("/customers")
    public StandardResponse createCustomer(@RequestBody Customer customer){
        details = new LinkedHashMap();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                        .getResponse();
        try{
            System.out.println("Inside the POST method of Spring CustomerApiController " + customer);
            if(ValidationUtils.validation(customer)){
                customersInterface.createCustomer_v1(customer);
                details.put("key", customer.getFirstName() + " " + customer.getLastName());
                details.put("reason", Messages.RESOURCE_CREATED);
                String message = "Customer Detail Created successfully";
                return ResponseUtils.createSuccessResponse(details, message);
            }

        } catch(CustomValidateException e){
            e.printStackTrace();
            System.out.println("Inside CustomException SpringupdateCustomer method " + e.toString());
            int scode = (response.getStatus() != 500)? 400: 500;
            response.setStatus(scode);
            return errorResponseShow(e);
        }
        return null;
    }

    @PutMapping("/customers")
    public StandardResponse updateCustomer(@RequestBody Customer updatedCustomer){
        details = new LinkedHashMap();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                        .getResponse();
        try{
            System.out.println("Inside the PUT method of Spring CustomerApiController " + updatedCustomer);
            if(ValidationUtils.validation(updatedCustomer)){
                customersInterface.updateCustomer_v1(updatedCustomer);
                details.put("key", "id :" + updatedCustomer.getId());
                details.put("reason", Messages.RESOURCE_UPDATED);
                String message = "Customer detail Updated Successfully";
                return ResponseUtils.createSuccessResponse(details, message);
            }

        } catch(CustomValidateException e){
            e.printStackTrace();
            System.out.println("Inside CustomException SpringupdateCustomer method " + e.toString());
            int scode = (response.getStatus() != 500)? 400: 500;
            response.setStatus(scode);
            return errorResponseShow(e);
        }
        return null;
    }

    @DeleteMapping("/customers/{id}")
    public StandardResponse deleteCustomer(@PathVariable("id") int id){
        details = new LinkedHashMap();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                        .getResponse();
        try{
            System.out.println("Inside the DELETE method of Spring CustomerApiController");
            customersInterface.deleteCustomer_v1(id);
            String message = "Customer id " + id + " deleted Successfully";
            details.put("key", id);
            details.put("reason", Messages.RESOURCE_DELETED);
            return ResponseUtils.createSuccessResponse(details, message);
        } catch(Exception e){
            e.printStackTrace();
            response.setStatus(400);
            return ResponseUtils.createErrorResponse(ResponseCode.INTERNAL_ERROR, e.getMessage(),
                                            Messages.RESOURCE_NOT_FOUND);

        }
    }

    public StandardResponse errorResponseShow(CustomValidateException e){
//        LinkedHashMap details = new LinkedHashMap();
//        details.put("key", e.getFeild());
        return ResponseUtils.createErrorResponse(e.getCode(),  e.getFeild(), " Please check the Details to resolve");
    }

}