package com.crm.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;

import com.opensymphony.xwork2.config.ConfigurationManager;

public class CustomActionMapper extends DefaultActionMapper{
    private final String BASE_PACKAGE = "com.crm.handler.";

    @Override
    public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager){
        String uri = request.getRequestURI();
        String redirectMethod;
        Map<String, ? extends Object> map;

        if(uri.contains("/demo")){
            String httpMethod = request.getMethod();
            map = request.getParameterMap();
            String[] parts = uri.split("/");
            String pathPoint = parts[parts.length - 1];
            String version = parts[parts.length - 2];
            version = version.equals("v1")? "v1": "v2";
            String resource = pathPoint.substring(0, pathPoint.length() - 1);

            request.setAttribute("handlerName", BASE_PACKAGE + version + "." + resource + "Handler");

            request.setAttribute("pojo", "com.crm.model." + resource);
            request.setAttribute("method", httpMethod);
            redirectMethod = resource.toLowerCase() + "Method";

        } else{
            System.out.println("enter inside CustomActionMapper " +request.getRequestURI());
            return super.getMapping(request, configManager);
        }

        return new ActionMapping("Zoho", "/demo", redirectMethod, (Map<String, Object>) map);
    }

}
