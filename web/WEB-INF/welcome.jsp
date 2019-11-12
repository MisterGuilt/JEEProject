<%@page import="java.util.ArrayList"%>
<%@page import="se.m1.model.beans.User"%>
<%@page import="se.m1.model.beans.Employee"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Management Project</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <style>
            table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
        ul {
          list-style-type: none;
          margin: 0;
          padding: 0;
          overflow: hidden;
          background-color: #333;
        }

        li {
          float: left;
        }

        li a {
          display: block;
          color: white;
          text-align: center;
          padding: 14px 16px;
          text-decoration: none;
        }

        li a:hover:not(.active) {
          background-color: #111;
        }

        .active {
          background-color: #4CAF50;
          height: 50px;
        }

        .person{
            background-color: lightblue;
        }
        </style>
    </head>
    <body>
    <ul>
            <form method='post' action='Controller'>
          <li><input type="submit" class="active" name="logout" value="Log Out"></li>
          </form>
          <li><a class="person">Hello ${user.username}, you are an ${user.rank}</a></li>
        </ul>
        <c:if test="${!empty errKey}"><h1 style="color:red;"><c:out value="${errKey}"/></h1></c:if>
        
            <form method='post' action='Controller'>
            <table border="1" class="table">
                <thead
                <tr>
                     <td><b>Selection</b></td>
                    <td><b>Name</b></td>
                    <td><b>First Name</b></td>
                    <td><b>Home Phone</b></td>
                    <td><b>Mobile Phone</b></td>
                    <td><b>Work Phone</b></td>
                    <td><b>Address</b></td>
                    <td><b>Postal Code</b></td>
                    <td><b>City</b></td>
                    <td><b>Email</b></td>
                </tr>
                </thead>
                <tbody
                <tr>
                    <c:forEach items="${empList}" var="employee"> 
                    <tr>
                        <td><input type="radio" name="id" value="${employee.id}"/></td>
                        <td>${employee.name}</td>
                        <td>${employee.firstname}</td>
                        <td>${employee.homePhone}</td>
                        <td>${employee.mobilePhone}</td>
                        <td>${employee.proPhone}</td>
                        <td>${employee.address}</td>
                        <td>${employee.postalCode}</td>
                        <td>${employee.city}</td>
                        <td>${employee.mail}</td>
                    </tr>
                    </c:forEach>
                    
                </tr>
                </tbody>
            </table>
       
            <input type="submit" name="add" value="Add Employee" ${(user.rank != "admin") ? "disabled" : ''}>
            <input type="submit" name="detail" value="Detail">
            <input type="submit" name="delete" value="Delete" ${(user.rank != "admin") ? "disabled" : ''}>
        </form>
    </body>
</html>
