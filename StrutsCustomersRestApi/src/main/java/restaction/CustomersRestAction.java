package restaction;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zoho.util.Messages;
import com.zoho.util.ResponseCode;
import com.zoho.util.ResponseUtils;
import com.zoho.util.StandardResponse;

import proxy.CustomersProxyHandler;
import restdao.CustomersDAO;
import restdao.CustomersInterface;
import restmodel.Customer;
import restvalidation.CustomValidateException;
import restvalidation.ValidationUtils;

public class CustomersRestAction extends ActionSupport
                                implements ModelDriven<Customer>, ServletRequestAware, ServletResponseAware{

    Customer customer = new Customer();
    List cutomersList;
    CustomersInterface customersDAO;
    StandardResponse result;
    int cid;
    static String errormessage;
    static int statusCode;
    String message;
    HttpServletRequest request;
    HttpServletResponse response;

    String idParam = "";
    String version = "";

    public CustomersRestAction(){

        customersDAO = (CustomersInterface) Proxy.newProxyInstance(
                                        CustomersDAO.class.getClassLoader(),
                                        new Class[]{ CustomersInterface.class},
                                        new CustomersProxyHandler(new CustomersDAO()));
    }

    @Override
    public Customer getModel(){
        return customer;
    }

    @Override
    public void setServletRequest(HttpServletRequest req){
        this.request = req;
    }

    @Override
    public void setServletResponse(HttpServletResponse res){
        this.response = res;
    }

    @Override
    public String execute() throws CustomValidateException{

        String method = request.getMethod();
        System.out.println("Request Method :" + method);
        switch(method){
        case "GET":
            return getAllCustomersVersion();
        case "POST":
            return createCustomer();
        case "PUT":
            return updateCustomer();
        case "DELETE":
            return deleteCustomer();

        default:
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            result = ResponseUtils.createErrorResponse(ResponseCode.INTERNAL_ERROR, null, Messages.INTERNAL_ERROR);
            return ERROR;

        }

    }

    public String getCustomerById(){
        LinkedHashMap details = new LinkedHashMap();
        try{
            ArrayList al = new ArrayList();
            al.add(customersDAO.getCustomerByID_v1(cid));
            System.out.println("Get customerByID :" + al);

            if(al.get(0) != null){
                setCutomersList(al);
                System.out.println("customerList :" + getCutomersList());
                message = "customer details are retrieved successfully";
                return SUCCESS;

            } else{
                message = "Customer not found";
                return ERROR;
            }
        } catch(Exception e){
            details.put("key", "id");
            throw new CustomValidateException(ResponseCode.INVALID_DATA, details, Messages.RESOURCE_NOT_FOUND);
        }
    }

    public String createCustomer(){
        LinkedHashMap details = new LinkedHashMap();
        try{
            System.out.println("Customer Name: " + customer.toString());
            if(ValidationUtils.validation(customer)){
                customersDAO.createCustomer_v1(customer);
                message = "Customer detail Created Successfully";
                details.put("key", customer.getFirstName() + " " + customer.getLastName());
                details.put("reason", Messages.RESOURCE_CREATED);
                result = ResponseUtils.createSuccessResponse(details, message);
            }
            return INPUT;
        } catch(CustomValidateException e){
            throw e;
        } catch(Exception e){
            throw new CustomValidateException("Error creating customer");
        }
    }

    public String updateCustomer(){
        LinkedHashMap details = new LinkedHashMap();
        try{
            System.out.println("Update Customer :" + customer);
            if(ValidationUtils.validation(customer)){
                customersDAO.updateCustomer_v1(customer);
                message = "Customer detail Updated Successfully";
                details.put("key", "id :" + customer.getId());
                details.put("reason", Messages.RESOURCE_UPDATED);
                result = ResponseUtils.createSuccessResponse(details, message);
            }
            return INPUT;
        } catch(CustomValidateException e){
            throw e;
        } catch(Exception e){
            throw new CustomValidateException("Error creating customer");
        }
    }

    public String deleteCustomer(){
        LinkedHashMap details = new LinkedHashMap();
        try{
            System.out.println("Enter into Delete method :" + cid);
            customersDAO.deleteCustomer_v1(cid);
            message = "Customer id " + cid + " deleted Successfully";

            details.put("key", cid);
            details.put("reason", Messages.RESOURCE_DELETED);
            result = ResponseUtils.createSuccessResponse(details, message);
            return INPUT;
         } catch(Exception e){
            e.printStackTrace();
            details.put("key", "id");
            throw new CustomValidateException(ResponseCode.INVALID_DATA, details, Messages.RESOURCE_NOT_FOUND);
        }
    }

    public String getAllCustomersVersion(){
        idParam = request.getParameter("cid");
        version = request.getRequestURI().contains("/v2/")? "v2": "v1";

        System.out.println("id Param: " + idParam);
        System.out.println("API Version: " + version);

        if(idParam == null){
            List<Map<String, Object>> allCustomers = customersDAO.getTotalCutomers_v1();
            cutomersList = allCustomers;
            System.out.println("customerList: " + cutomersList);
            message = "All the customer details are retrieved successfully";
            return SUCCESS;

        } else{
            return getCustomerById();
        }
    }

    public String getAllCustomers(){

        String idParam = request.getParameter("cid");

        System.out.println("id Param :" + idParam);
        if(idParam == null){
            cutomersList = customersDAO.getTotalCutomers_v1();
            System.out.println("customerList :" + cutomersList);
            message = "All the customer details are retrieved successfully";
            return SUCCESS;
        } else{
            getCustomerById();
        }
        return (cutomersList != null)? SUCCESS: INPUT;
    }

    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public List<Customer> getCutomersList(){
        return cutomersList;
    }

    public void setCutomersList(List cutomersList){
        this.cutomersList = cutomersList;
    }

    public CustomersInterface getCustomersDAO(){
        return customersDAO;
    }

    public void setCustomersDAO(CustomersInterface customersDAO){
        this.customersDAO = customersDAO;
    }

    public int getCid(){
        return cid;
    }

    public void setCid(int cid){
        this.cid = cid;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public HttpServletRequest getRequest(){
        return request;
    }

    public void setRequest(HttpServletRequest request){
        this.request = request;
    }

    public StandardResponse getResult(){
        return result;
    }

    public void setResult(StandardResponse result){
        this.result = result;
    }

    public static String getErrormessage(){
        return errormessage;
    }

    public static void setErrormessage(String errormessage){
        CustomersRestAction.errormessage = errormessage;
    }

    public static int getStatusCode(){
        return statusCode;
    }

    public static void setStatusCode(int statusCode){
        CustomersRestAction.statusCode = statusCode;
    }


}
