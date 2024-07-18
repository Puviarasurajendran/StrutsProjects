package com.crm.action;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.crm.handler.v1.CustomerHandler;
import com.crm.handler.v1.ProductHandler;
import com.crm.handler.v1.SalesOrderHandler;
import com.crm.handler.v1.VendorHandler;
import com.crm.model.Customer;
import com.crm.model.Product;
import com.crm.model.Vendor;
import com.crm.proxy.VersioningProxyFactoryUtil;
import com.crm.validation.CustomValidateException;
import com.crm.validation.FeildValidationUtils;
import com.crm.validation.ResponseCode;
import com.opensymphony.xwork2.ActionSupport;

import jsonbodyutils.JsonBodyUtils;

public class SMRouteAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

    private static final long serialVersionUID = 1L;
    HttpServletRequest request;
    HttpServletResponse response;
    JsonBodyUtils jsonUtil = new JsonBodyUtils();
    FeildValidationUtils validate = new FeildValidationUtils();
    int id;
    String result;
    String clazz;
    private static String jsonResponse;

    @Override
    public void setServletRequest(HttpServletRequest req){
        this.request = req;
    }

    @Override
    public void setServletResponse(HttpServletResponse res){
        this.response = res;
    }

    public String productMethod() throws Exception{

        String httpMethod = request.getMethod();

        clazz = (String) request.getAttribute("handlerName");
        ProductHandler handlerAction = (ProductHandler) VersioningProxyFactoryUtil.createProxy(clazz);

        switch(httpMethod){
        case "GET":
            result = jsonUtil.responseWriter(response, handlerAction.getProductsData());
            return result;
        case "POST":
            result = handlerAction.postProductData((Product) jsonUtil.readResponseBody(request));
            return result;
        case "PUT":
            result = handlerAction.putProductData((Product) jsonUtil.readResponseBody(request));
            return result;
        case "DELETE":
            if(cheackId(id)) {
            result = handlerAction.deleteProductData(id);
            }
            return result;
        default:
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            throw new UnsupportedOperationException("HTTP method not supported: " + httpMethod);
        }

    }

    public String customerMethod() throws Exception{

        String httpMethod = request.getMethod();
        clazz = (String) request.getAttribute("handlerName");

        CustomerHandler handlerAction = (CustomerHandler) VersioningProxyFactoryUtil.createProxy(clazz);

        switch(httpMethod){
        case "GET":
            result = jsonUtil.responseWriter(response, handlerAction.getCustomersData());
            return result;
        case "POST":
            result = handlerAction.postCustomerData((Customer) jsonUtil.readResponseBody(request));
            return result;
        case "PUT":
            Customer c = (Customer) jsonUtil.readResponseBody(request);
            result = handlerAction.putCustomerData(c);
            return result;
        case "DELETE":
            if(cheackId(id)) {
            result = handlerAction.deleteCustomerData(id);
            }
            return result;
        default:
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            throw new UnsupportedOperationException("HTTP method not supported: " + httpMethod);
        }

    }

    public String vendorMethod() throws Exception{

        String httpMethod = request.getMethod();
        clazz = (String) request.getAttribute("handlerName");

        VendorHandler handlerAction = (VendorHandler) VersioningProxyFactoryUtil.createProxy(clazz);

        switch(httpMethod){
        case "GET":
            result = jsonUtil.responseWriter(response, handlerAction.getVendorsData());
            return result;
        case "POST":
            result = handlerAction.postvendorData((Vendor) jsonUtil.readResponseBody(request));
            return result;
        case "PUT":
            result = handlerAction.putVendorData((Vendor) jsonUtil.readResponseBody(request));
            return result;
        case "DELETE":
            if(cheackId(id)) {
            result = handlerAction.deleteVendorData(id);
            }
            return result;
        default:
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            throw new UnsupportedOperationException("HTTP method not supported: " + httpMethod);
        }

    }

    public String salesorderMethod() throws Exception{

        String httpMethod = request.getMethod();
        clazz = (String) request.getAttribute("handlerName");

        SalesOrderHandler handlerAction = (SalesOrderHandler) VersioningProxyFactoryUtil.createProxy(clazz);

        switch(httpMethod){
        case "GET":
            result = jsonUtil.responseWriter(response, handlerAction.getSalesOrderData());
            return result;
        case "POST":
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            throw new UnsupportedOperationException("HTTP method not supported: " + httpMethod);
        case "PUT":
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            throw new UnsupportedOperationException("HTTP method not supported: " + httpMethod);
        case "DELETE":
            if(cheackId(id)) {
            result = handlerAction.deleteSalesOrderData(id);
            }
            return result;
        default:
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            throw new UnsupportedOperationException("HTTP method not supported: " + httpMethod);
        }

    }

    public String getResult(){
        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public static String getJsonResponse(){
        return jsonResponse;
    }

    public static void setJsonResponse(String jsonResponse){
        SMRouteAction.jsonResponse = jsonResponse;
    }

    public boolean cheackId(int id) {
        if(id==0){
            LinkedHashMap<String, Object>
            details = new LinkedHashMap<>();
            details.put("key", "id");
            details.put("Resolution", "uri doesn't contains param id ");
            CustomValidateException e = new CustomValidateException(ResponseCode.MANDATORY_NOT_FOUND, details, "error",
                                            "please cheack details to reslove");
            jsonUtil.writeResponseMessage(response, (HashMap) e.toMap());
            return false;
        }
     return true;
}


}
