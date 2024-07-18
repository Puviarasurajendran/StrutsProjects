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
import com.crm.model.Vendor;
import com.crm.validation.CustomValidateException;
import com.crm.validation.ResponseCode;

import jsonbodyutils.JsonBodyUtils;

public class VendorHandler{

    private DAOManager daoManager = new DAOManager();
    private HttpServletResponse response = ServletActionContext.getResponse();

    protected JsonBodyUtils jsonUtil = new JsonBodyUtils();
    protected LinkedHashMap<String, Object> details = new LinkedHashMap<>();

    public List<Vendor> getVendorsData(){
        try{
            List<Map<String, Object>> dataList = daoManager.getAllData("SELECT * FROM Vendors");

            return dataList.stream()
                                            .map(row -> {
                                                Vendor vendor = new Vendor();
                                                vendor.setVendorId((Integer) row.get("vendor_id"));
                                                vendor.setName((String) row.get("name"));
                                                vendor.setContactName((String) row.get("contact_name"));
                                                vendor.setContactPhone((String) row.get("contact_phone"));
                                                vendor.setAddress((String) row.get("address"));
                                                vendor.setVendor_email((String) row.get("vendor_email"));
                                                return vendor;
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

    public String putVendorData(Vendor vendor){
        try{

            String sql = "UPDATE Vendors SET vendor_id = ?, name = ?, contact_name = ?, contact_phone = ?, address = ?, vendor_email = ? WHERE vendor_id ="
                                            + vendor.getVendorId();
            daoManager.updateData(sql, vendor, vendor.getVendorId());
            details = new LinkedHashMap<>();
            details.put("key", "id");
            details.put("Resolution", "vendor id " + vendor.getVendorId() + " details updated successfully ");
            CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "success",
                                            "vendor details Updated successfully");
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());

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

    public String postvendorData(Vendor vendor){
        try{
            daoManager.addNewData("Vendors", vendor);
            details = new LinkedHashMap<>();
            details.put("key", vendor.getName());
            details.put("Resolution", "Vendor " + vendor.getName() + "created successfully");
            CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "success",
                                            "vendor created successfully");
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            response.setStatus(HttpServletResponse.SC_CREATED);

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

    public String deleteVendorData(int id){
        try{
            if(daoManager.deleteData("Vendors", id)){
                details = new LinkedHashMap<>();
                details.put("key", id);
                CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "success",
                                                "Vendor Deleted successfully");
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

}
