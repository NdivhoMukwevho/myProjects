<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>update Student</title>
</head>
<body>
<form method="post" action="updateServ" border="1">
            <center>
            <br>
            Update the current student info below<br><br>
            <% HttpSession ss = request.getSession();
               ResultSet studInfo = (ResultSet)ss.getAttribute("updStud");
            %>
            <table>
                <th></th><th></th>
                <tr><td>Student Number:</td><td><input type="number" required="" placeholder="Enter Student Number" value= <%=studInfo.getInt(1) %> name="stdnum"></td></tr>
                <tr><td>Firstname:</td><td><input type="text" required="" placeholder="Enter Firstname" value= <%=studInfo.getString(2) %> name="firstname"></td></tr>
                <tr><td>Surname:</td><td><input type="text" required="" placeholder="Enter Surname" value= <%=studInfo.getString(3) %> name="surname"></td></tr>
                <tr><td>Course Code:</td><td><input type="text" required="" placeholder="Enter Course Code" value= <%=studInfo.getString(4) %> name="ccode"></td></tr>
                <tr><td>Course Description:</td><td><input type="text" required="" placeholder="Enter Course Description" value= <%=studInfo.getString(5) %> name="cdesc"></td></tr>
                <tr><td>Grade:</td><td><input type="text" required="" placeholder="Enter Grade" value= <%=studInfo.getString(6) %> name="grade"></td></tr>
                <tr><td></td><td><input type="hidden" value= <%=studInfo.getInt(1) %> name="actstdnum"></td></tr>
                <tr><td></td><td><input type="hidden" value= <%=studInfo.getString(4) %> name="actcode"></td></tr>
            </table><br>
            <input type="submit" value="update">
            </center>
   </form>
</body>
</html>