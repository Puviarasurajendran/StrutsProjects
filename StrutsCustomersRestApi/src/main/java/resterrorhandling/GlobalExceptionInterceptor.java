package resterrorhandling;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zoho.util.ResponseCode;
import com.zoho.util.StandardResponse;

import restvalidation.CustomValidateException;

public class GlobalExceptionInterceptor extends AbstractInterceptor{

    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation invocation) throws CustomValidateException,Exception{
        try{
            return invocation.invoke();

        } catch(CustomValidateException e){
            StandardResponse responseObj = new StandardResponse(e.getCode(), e.getFeild(), "error", e.getMessage());

            HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext()
                                            .get(StrutsStatics.HTTP_RESPONSE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            invocation.getStack().set("result", responseObj);
            return "error";
        } catch(Exception e){
            StandardResponse responseObj = new StandardResponse(ResponseCode.INTERNAL_ERROR, null, "error",
                                            e.getMessage());

            HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext()
                                            .get(StrutsStatics.HTTP_RESPONSE);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            invocation.getStack().set("result", responseObj);
            return "error";
        }
    }
}