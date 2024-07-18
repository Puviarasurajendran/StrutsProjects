package com.crm.handler.v2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.crm.model.SalesOrder;

public class SalesOrderHandler extends com.crm.handler.v1.SalesOrderHandler{


    public  Map<String, List<SalesOrder>> groupProducts(List<SalesOrder> salesList) {
        return salesList.stream()
                .collect(Collectors.groupingBy(SalesOrder::getCustomerName));
    }
}
