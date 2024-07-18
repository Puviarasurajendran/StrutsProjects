package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zoho.util.ResponseCode;

import restvalidation.CustomValidateException;

public class CustomersProxyHandler implements InvocationHandler{

    public final Object target;

    public CustomersProxyHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{

        System.out.println("Inside Proxy Handler invoke method");

        HttpServletRequest request = ServletActionContext.getRequest();

        String version = getVersionFromRequest(request);
        String versionedMethodName = removeVersionMethod(method.getName()) + version;
        System.out.println("After the method handling via proxy " + versionedMethodName);
        try{
            Method versionSpecificMethod = target.getClass().getMethod(versionedMethodName, method.getParameterTypes());

            return versionSpecificMethod.invoke(target, args);
        } catch(NoSuchMethodException e){
            throw new RuntimeException("Method not found for version: " + version);
        } catch(Exception e){
            System.out.println("Inside proxy after cauting exeption while invoke ");
            throw new CustomValidateException(ResponseCode.INTERNAL_ERROR, "unknown",
                                            "Unexpected and unhandled exception in the server. Contact support team");
        }

    }

    private String getVersionFromRequest(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        if(requestURI.contains("/v2/")){
            return "v2";
        } else{
            return "v1";
        }
    }

    private String removeVersionMethod(String method){
        System.out.println("method :" + method.substring(0, (method.length() - 2)));
        return method.substring(0, method.length() - 2);
    }
}
