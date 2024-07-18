<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>zoho login</title>
<style>
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        background-color: #f9f9f9; /* Optional: Adds a light background color */
    }
    .container {
        border: 1px solid #ccc;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        background-color: #fff;
        text-align: center;
    }
    form {
        display: inline-block;
        margin: 0;
    }
    label{
        display: block;
        text-align: left;
    }
    input{
       text-align: center;
    }
</style>
</head>
<body>
    <div class="container">
        <form method="post" action="./login">
            <h1>Sign In</h1>
            <label for="uname">User Name :</label>
            <input type="text" id="username" name="username" required>
             <br></br>
            <label for="psswd">Password :</label>
            <input type="password" id="password" name="password" required>
            <br></br>
            <input type="submit" value="login">
        </form>
    </div>
</body>
</html>
