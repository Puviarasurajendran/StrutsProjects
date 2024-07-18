package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zoho.util.ResponseCode;

import restdao.CustomersInterface;
import restvalidation.CustomValidateException;

@Component
public class VersionProxyHandlerUtils implements InvocationHandler{

    private final CustomersInterface target;

    @Autowired
    public VersionProxyHandlerUtils(CustomersInterface target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        System.out.println("Inside Spring VersionProxyHandlerUtils invoke method");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                        .getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                                        .getResponse();
        String version = getVersionFromRequest(request);
        String versionedMethodName = removeVersionMethod(method.getName()) + version;
        System.out.println("After the method handling via proxy " + versionedMethodName);

        try{
            Method versionSpecificMethod = target.getClass().getMethod(versionedMethodName, method.getParameterTypes());
            return versionSpecificMethod.invoke(target, args);

        } catch(NoSuchMethodException e){
            throw new RuntimeException("Method not found for version: " + version);
        } catch(Exception e){
            System.out.println("Inside VersionProxyHandlerUtils after cauting exeption while invoke ");
            response.setStatus(500);
            throw new CustomValidateException(ResponseCode.INTERNAL_ERROR, "unknown",
                                            "Unexpected and unhandled exception in the server. Contact support team");
        }
    }

    private String getVersionFromRequest(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return requestURI.contains("/v2/")? "v2": "v1";
    }

    private String removeVersionMethod(String method){
        System.out.println("method :" + method.substring(0, (method.length() - 2)));
        return method.substring(0, method.length() - 2);
    }
}
