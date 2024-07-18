package com.crm.dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.crm.validation.CustomValidateException;
import com.crm.validation.ResponseCode;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import jsonbodyutils.JsonBodyUtils;

public class DAOManager{

    protected HttpServletResponse response = ServletActionContext.getResponse();
    protected JsonBodyUtils jsonUtil = new JsonBodyUtils();

    public List<Map<String, Object>> getAllData(String query){
        List<Map<String, Object>> datas = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = DBConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while(rs.next()){
                Map<String, Object> row = new HashMap<>();
                for(int i = 1; i <= columnCount; i++){
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(columnName);
                    row.put(columnName, columnValue);
                }
                datas.add(row);
            }
        } catch(Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            CustomValidateException ex = new CustomValidateException(ResponseCode.INTERNAL_ERROR, null, "error",
                                            "Please contact support team for further process");
            jsonUtil.writeResponseMessage(response, (HashMap) ex.toMap());
            e.printStackTrace();
        } finally{
            try{
                if(rs != null){
                    rs.close();
                }
                if(stmt != null){
                    stmt.close();
                }
                if(con != null){
                    con.close();
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return datas;
    }

    public <T extends DatabaseEntity> void addNewData(String tableName, T entity){

        String sql = constructInsertStatement(tableName, entity.toObjectArray());

        try (Connection conn = DBConnection.getConnection();
                                        PreparedStatement stmt = conn.prepareStatement(sql)){

            setParameters(stmt, entity.toObjectArray());

            int rowsInserted = stmt.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("Data inserted successfully into " + tableName);
            } else{
                System.out.println("Failed to insert data into " + tableName);
            }
        } catch(Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            CustomValidateException ex = new CustomValidateException(ResponseCode.INTERNAL_ERROR, null, "error",
                                            "Please contact support team for further process");
            jsonUtil.writeResponseMessage(response, (HashMap) ex.toMap());
            e.printStackTrace();
        }
    }

    public <T extends DatabaseEntity> void updateData(String query, T entity, int id){

        try (Connection conn = DBConnection.getConnection();
                                        PreparedStatement stmt = conn.prepareStatement(query)){

            setParameters(stmt, entity.toObjectArray());
            int rowsUpdated = stmt.executeUpdate();
            if(rowsUpdated > 0){
                System.out.println("Data updated successfully ");
            } else{
                System.out.println("Failed to update data ");
            }
        } catch(Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            CustomValidateException ex = new CustomValidateException(ResponseCode.INTERNAL_ERROR, null, "error",
                                            "Please contact support team for further process");
            jsonUtil.writeResponseMessage(response, (HashMap) ex.toMap());
            e.printStackTrace();
        }
    }

    public <T extends DatabaseEntity> boolean deleteData(String tableName, int id){
        String sql = constructDeleteStatement(tableName, id);

        try (Connection conn = DBConnection.getConnection();
                                        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if(rowsDeleted > 0){
                System.out.println("Data deleted successfully from " + tableName);
                return true;
            } else{
                LinkedHashMap<String,Object> details= new LinkedHashMap<>();
                details.put("key","id");
                details.put("Resolution", "Customer id doesn't exists");
                CustomValidateException ex = new CustomValidateException(ResponseCode.INVALID_DATA, null, "error",
                                                "Please contact support team for further process");
                jsonUtil.writeResponseMessage(response, (HashMap) ex.toMap());
                return false;
            }
        } catch(Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            CustomValidateException ex = new CustomValidateException(ResponseCode.INTERNAL_ERROR, null, "error",
                                            "Please contact support team for further process");
            jsonUtil.writeResponseMessage(response, (HashMap) ex.toMap());
            e.printStackTrace();
        }
        return false;
    }

    private String constructInsertStatement(String tableName, Object[] values){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append(" VALUES (");

        for(int i = 0; i < values.length; i++){
            if(i > 0){
                sql.append(", ");
            }
            sql.append("?");
        }
        sql.append(")");
        return sql.toString();
    }

    private String constructDeleteStatement(String tableName, int id){
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName).append(" WHERE ");

        String tableId = tableName.substring(0, tableName.length() - 1) + "_id";

        sql.append(tableId + " = ?");
        return sql.toString();
    }

    private void setParameters(PreparedStatement stmt, Object[] values) throws SQLException{
        for(int i = 0; i < values.length; i++){
            stmt.setObject(i + 1, values[i]);
        }
    }

    public boolean saveImageFile(String fileName, InputStream fileContent) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO images (imagename, imagedata) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, fileName);
            statement.setBlob(2, fileContent);

            int row = statement.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Blob getFileById(int imageId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT imagedata FROM images WHERE imageId = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, imageId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBlob("imagedata");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFileNameById(int imageId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT imagename FROM images WHERE imageid = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, imageId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("imagename");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

