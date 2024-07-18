package com.crm.validation;

import java.sql.SQLException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

public class ValidationInterceptor extends AbstractInterceptor{


    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception{
        try{
            return invocation.invoke();
        }catch(UnsupportedOperationException e){
            ValueStack stack = invocation.getStack();
            stack.set("jsonResponse", new CustomValidateException(ResponseCode.METHOD_NOT_ALLOWED, null, "error",
                                            "HTTP method not supported"));
            return "error";
        }
        catch(CustomValidateException e){
            ValueStack stack = invocation.getStack();
            stack.set("jsonResponse", e);
            return "error";
        } catch(SQLException e){
            ValueStack stack = invocation.getStack();
            stack.set("jsonResponse", new CustomValidateException(ResponseCode.INTERNAL_ERROR, null, "error",
                                            "Please contact support team for further process" ));
            return "error";
          }catch(Exception e){
            ValueStack stack = invocation.getStack();
            stack.set("jsonResponse", new CustomValidateException(ResponseCode.INTERNAL_ERROR, null, "error",
                                            "Please contact support team for further process" ));
            return "error";
        }
    }

}
