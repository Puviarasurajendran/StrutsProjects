package jsonbodyutils;


import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm.validation.CustomValidateException;
import com.crm.validation.ResponseCode;
import com.google.gson.Gson;

public class JsonBodyUtils{

    HttpServletResponse resp= ServletActionContext.getResponse();
    public String responseWriter(HttpServletResponse response, List list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.getWriter().close();
        } catch(Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    public Object readResponseBody(HttpServletRequest request) throws Exception{
        BufferedReader reader;
        try{
            reader = request.getReader();

            String line;
            String item = "";
            while((line = reader.readLine()) != null){
                item += line;
            }
            String pojoName = (String) request.getAttribute("pojo");
            Class pojoClass;
            pojoClass = Class.forName(pojoName);
            Gson gson = new Gson();
            Object c = gson.fromJson(item.toString(), pojoClass);

            return c;

        } catch(Exception e){
            e.printStackTrace();
            resp.setStatus(400);
            throw new CustomValidateException(ResponseCode.INVALID_DATA, null, "error", "Invalid json object");

        }
    }

    public String writeResponseMessage(HttpServletResponse response, HashMap json){
        Gson gson = new Gson();
        String json1 = gson.toJson(json);
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json1);
            response.getWriter().close();
        } catch(Exception e){

            e.printStackTrace();
            return "error";
        }
        return "success";
    }
}
