package com.crm.handler.v1;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm.dao.DAOManager;
import com.crm.model.SalesOrder;
import com.crm.model.Vendor;
import com.crm.validation.CustomValidateException;
import com.crm.validation.ResponseCode;

import jsonbodyutils.JsonBodyUtils;

public class SalesOrderHandler{

    private DAOManager daoManager = new DAOManager();
    private HttpServletResponse response = ServletActionContext.getResponse();

    protected JsonBodyUtils jsonUtil = new JsonBodyUtils();
    protected LinkedHashMap<String, Object> details = new LinkedHashMap<>();

    public List<SalesOrder> getSalesOrderData(){
        try{
            String query = "SELECT so.customer_id, so.product_id,  so.quantity, so.order_date, so.total_amount, " +
                                            "concat(c.first_name ,' ',c.last_name) as customer_name " +
                                            "FROM SalesOrders so " +
                                            "JOIN Products p ON so.product_id = p.product_id " +
                                            "JOIN Customers c ON so.customer_id = c.customer_id";

            List<Map<String, Object>> dataList = daoManager.getAllData(query);

            return dataList.stream()
                                            .map(row -> {
                                                SalesOrder salesOrder = new SalesOrder();
                                                salesOrder.setCustomerId((Integer) row.get("customer_id"));
                                                salesOrder.setProduct((String) row.get("product_name"));
                                                salesOrder.setQuantity((Integer) row.get("quantity"));
                                                salesOrder.setCustomerName((String) row.get("customer_name"));
                                                salesOrder.setCustomerPhone((String) row.get("customer_phone"));
                                                BigDecimal amountBigDecimal = (BigDecimal) row.get("total_amount");
                                                salesOrder.setAmount(amountBigDecimal.doubleValue());
                                                salesOrder.setOrderDate((java.sql.Date) row.get("order_date"));
                                                salesOrder.setProductId((Integer) row.get("product_id"));
                                                return salesOrder;
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

    public String putSalesOrderData(Vendor vendor){
        try{

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
        return "ERROR";
    }

    public String postSalesOrderData(SalesOrder salesOrder){
        try{

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
        return "ERROR";
    }

    public String deleteSalesOrderData(int id){
        try{
            if(daoManager.deleteData("SalesOrders", id)){
                details = new LinkedHashMap<>();
                details.put("key", id);
                CustomValidateException e = new CustomValidateException(ResponseCode.SUCCESS, details, "success",
                                                "SalesOrder Deleted successfully");
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
