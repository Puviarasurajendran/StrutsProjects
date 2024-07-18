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
import com.crm.model.Product;
import com.crm.validation.CustomValidateException;
import com.crm.validation.ProductFieldValidation;
import com.crm.validation.ResponseCode;

import jsonbodyutils.JsonBodyUtils;

public class ProductHandler {

    private DAOManager daoManager = new DAOManager();
    private HttpServletResponse response = ServletActionContext.getResponse();

    protected JsonBodyUtils jsonUtil = new JsonBodyUtils();
    protected LinkedHashMap<String, Object> details = new LinkedHashMap<>();

	public ProductHandler() {
		super();
	}

    public static Map<String, List<Product>> groupProducts(List<Product> productList) {
        return productList.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    public List<Product> getProductsData() {
        try {
            List<Map<String, Object>> dataList = daoManager.getAllData("SELECT * FROM Products");

            return dataList.stream()
                    .map(row -> {
                        Product product = new Product();
                        product.setProductId((Integer) row.get("product_id"));
                        product.setProductName((String) row.get("name"));
                        product.setCategory((String) row.get("category"));
                        BigDecimal priceBigDecimal = (BigDecimal) row.get("price");
                        product.setPrice(priceBigDecimal.doubleValue());
                        product.setStock((Integer) row.get("stock"));
                        product.setVendorId(checkNull(row.get("vendor_id")));
                        return product;
                    })
                    .collect(Collectors.toList());

        } catch(Exception e) {
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

    public String putProductData(Product p){
        try{

                if(validationCheckUpdate(p)) {
                String sql = "UPDATE Products SET product_id = ? ,name = ?, category = ?, price = ?, stock = ?, vendor_id = ? WHERE product_id ="+p.getProductId();
                daoManager.updateData(sql, p, p.getProductId());
                details = new LinkedHashMap<>();
                details.put("key", "id");
                details.put("Resolution", "Product id "+p.getProductId()+" details updated successfully ");
                CustomValidateException e= new CustomValidateException(ResponseCode.SUCCESS, details, "success", "Product details Updated successfully");
                jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
                return "success";
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
        return "ERROR";
    }

    public String postProductData(Product product){
        try{
            if(validationCheck(product)) {
            daoManager.addNewData("Products", product);
            details = new LinkedHashMap<>();
            details.put("key", product.getProductName());
            details.put("Resolution", "Product "+product.getProductName()+"created successfully");
            CustomValidateException e= new CustomValidateException(ResponseCode.SUCCESS, details, "success", "Customer created successfully");
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            response.setStatus(HttpServletResponse.SC_CREATED);
            return "success";
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
        return "ERROR";
    }

    public String deleteProductData(int id){
        try{
            if(daoManager.deleteData("Products", id)){
                    details =new LinkedHashMap<>();
                    details.put("key", id);
                    CustomValidateException e= new CustomValidateException(ResponseCode.SUCCESS, details, "success", "Product Deleted successfully");
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

    public boolean validationCheck(Product p) throws CustomValidateException, IOException {
        try {
            ProductFieldValidation validate = new ProductFieldValidation();
        return validate.validateProduct(p);
        }catch (CustomValidateException e) {
             jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            return false;
        }
     }

    public boolean validationCheckUpdate(Product p) throws CustomValidateException, IOException {
       if(p.getProductId()>0) {
           return validationCheck(p);
       }
       else {
           details = new LinkedHashMap<>();
           details.put("key", "id");
           details.put("Resolution", "This key Mandatory & can't accept null value");
           CustomValidateException e= new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND, details, "error", "please cheack details to reslove");
           jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
           return false;
       }
     }

}
