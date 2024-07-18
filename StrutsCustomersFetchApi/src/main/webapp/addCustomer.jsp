<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Add Customer</title>
</head>
<body>
    <h1>Add New Customer</h1>
    <s:form action="createCustomer" namespace="/customer" method="post">
        <s:textfield name="firstName" label="First Name" />
        <s:textfield name="lastName" label="Last Name" />
        <s:textfield name="age" label="Age" />
        <s:textfield name="country" label="Country" />
        <s:textfield name="email" label="Email" />
        <s:submit value="Add Customer" />
    </s:form>
</body>
</html>
