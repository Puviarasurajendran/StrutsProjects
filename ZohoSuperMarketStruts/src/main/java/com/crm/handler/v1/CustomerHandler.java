package com.crm.handler.v1;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm.dao.DAOManager;
import com.crm.model.Customer;
import com.crm.validation.CustomValidateException;
import com.crm.validation.FeildValidationUtils;
import com.crm.validation.ResponseCode;

import jsonbodyutils.JsonBodyUtils;

public class CustomerHandler{

    private DAOManager daoManager = new DAOManager();
    protected HttpServletResponse response = ServletActionContext.getResponse();

    protected JsonBodyUtils jsonUtil = new JsonBodyUtils();
    protected LinkedHashMap<String, Object> details = new LinkedHashMap<>();

    public CustomerHandler(){
        super();
    }

    public List getCustomersData(){
        try{
            List<Map<String, Object>> dataList = daoManager.getAllData("SELECT * FROM Customers");
            return dataList.stream()
                                            .map(row -> {
                                                Customer customer = new Customer();
                                                customer.setCustomerId((Integer) row.get("customer_id"));
                                                customer.setFirstName((String) row.get("first_name"));
                                                customer.setLastName((String) row.get("last_name"));
                                                customer.setEmail((String) row.get("email"));
                                                customer.setPhone((String) row.get("phone"));
                                                customer.setAddress((String) row.get("address"));
                                                // customer.setCreditPoints((Integer)
                                                // checkNull(row.get("creadit_points")));
                                                return customer;
                                            })
                                            .collect(Collectors.toList());

        } catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Integer checkNull(Object obj){
        if(obj == null){
            return 0;
        } else{
            return (Integer) obj;
        }

    }

    public String putCustomerData(Customer c) throws CustomValidateException, IOException{
        try{
            if(validationCheckUpdate(c)){
                String sql = "UPDATE Customers SET  customer_id = ?, first_name = ?, last_name = ?, email = ?, phone = ?,address = ?,creadit_points = ? WHERE customer_id = "
                                                + c.getCustomerId();
                daoManager.updateData(sql, c, c.getCustomerId());
                details = new LinkedHashMap<>();
                details.put("key", "id");
                details.put("Resolution", "Customer id " + c.getCustomerId() + " details updated successfully ");
                CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "success",
                                                "Customer Updated successfully");
                jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());

                return "success";
            }
        } catch(CustomValidateException e){
            throw e;
        }
        return "error";
    }

    public String postCustomerData(Customer customer){
        try{

            if(validationCheck(customer)){
                daoManager.addNewData("Customers", customer);
                details = new LinkedHashMap<>();
                details.put("key", customer.getFirstName() + " " + customer.getLastName());
                details.put("Resolution", "Customer " + customer.getFirstName() + " " + customer.getLastName()
                                                + "created successfully");
                CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "success",
                                                "Customer created successfully");
                jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else{
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer data.");
            }
        } catch(Exception e){
            e.printStackTrace();
            try{
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                                "An error occurred while adding a customer.");
            } catch(IOException ioException){
                ioException.printStackTrace();
            }
            return "ERROR";
        }
        return "success";
    }

    public String deleteCustomerData(int id){
        try{
            if(daoManager.deleteData("Customers", id)){
                details = new LinkedHashMap<>();
                details.put("key", id);
                CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "success",
                                                "Customer Deleted successfully");
                jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
                return "success";
            }
        } catch(Exception e){
            e.printStackTrace();
            try{
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                                "An error occurred while datele a customer");
            } catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
        return "ERROR";
    }

    public boolean validationCheck(Customer c) throws CustomValidateException, IOException{
        try{
            FeildValidationUtils validate = new FeildValidationUtils();
            return validate.validationCustomer(c);
        } catch(CustomValidateException e){
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            return false;
        }
    }

    public boolean validationCheckUpdate(Customer c) throws CustomValidateException, IOException{
        if(c.getCustomerId() > 0){
            return validationCheck(c);
        } else{
            details = new LinkedHashMap<>();
            details.put("key", "id");
            details.put("Resolution", "This key Mandatory & can't accept null value");
            CustomValidateException e = new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND, details, "error",
                                            "please cheack details to reslove");
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            return false;
        }
    }
}
