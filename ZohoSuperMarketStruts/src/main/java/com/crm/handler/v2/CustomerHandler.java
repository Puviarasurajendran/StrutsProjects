package com.crm.handler.v2;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.crm.dao.DAOManager;
import com.crm.model.Customer;
import com.crm.model.Customer.FullName;
import com.crm.validation.CustomValidateException;
import com.crm.validation.ResponseCode;

public class CustomerHandler extends com.crm.handler.v1.CustomerHandler{

    private DAOManager daoManager= new DAOManager();

    @Override
    public List getCustomersData() {
        try {
            List<Map<String, Object>> dataList = daoManager.getAllData("SELECT * FROM Customers");
            return dataList.stream()
                    .map(row -> {
                        Customer customer = new Customer();
                        customer.setCustomerId((Integer) row.get("customer_id"));
                        customer.setFullname(new FullName((String) row.get("first_name"), (String) row.get("last_name")));
                        customer.setEmail((String) row.get("email"));
                        customer.setPhone((String) row.get("phone"));
                        customer.setAddress((String) row.get("address"));
                        customer.setCreditPoints(checkNull(row.get("creadit_points")));
                        return customer;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public String putCustomerData(Customer c) throws CustomValidateException{
        try {
            if(validationV2(c)) {
              c.setFirstName(c.getFullname().getFirstName());
              c.setLastName(c.getFullname().getLastName());
              if(super.validationCheckUpdate(c)) {
                String sql = "UPDATE Customers SET  customer_id = ?, first_name = ?, last_name = ?, email = ?, phone = ?,address = ?,creadit_points = ? WHERE customer_id = "+c.getCustomerId();
                daoManager.updateData(sql, c, c.getCustomerId());
                super.details = new LinkedHashMap<>();
                details.put("key", "id");
                details.put("Resolution", "Customer id "+c.getCustomerId()+" details updated successfully ");
                CustomValidateException e= new CustomValidateException(ResponseCode.SUCCESS, details, "success", "Customer Updated successfully");
                jsonUtil.writeResponseMessage(super.response, (HashMap) e.toMap());
                return "success";
              }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
        return "ERROR";
    }


    @Override
    public String postCustomerData(Customer c) {
        try {
           if(validationV2(c)) {
            c.setFirstName(c.getFullname().getFirstName());
            c.setLastName(c.getFullname().getLastName());
            daoManager.addNewData("Customers", c);
            super.postCustomerData(c);
            return "success";
           }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
        return "ERROR";
    }

    public boolean validationV2(Customer c) {
        if(c.getFullname()==null) {
            super.details = new LinkedHashMap<>();
            details.put("key", "Fullname");
            details.put("Resolution", "This key Mandatory & can't accept null value");
            CustomValidateException e= new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND, details, "error", "validation error please cheack details to resolve");
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            return false;
        }

        return true;
    }

}
