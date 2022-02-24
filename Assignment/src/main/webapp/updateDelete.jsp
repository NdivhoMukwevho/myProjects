<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update or Delete Student</title>
</head>
<body>
   <form method="post" action="updDelServ">
            <center>
            <br>
            Enter the below to select which student to update or delete<br><br>
            <table>
                <th></th><th></th>
                <tr><td>Student Number:</td><td><input type="number" required="" placeholder="Enter Student Number" name="stdnum"></td></tr>
                <tr><td>Course Code:</td><td><input type="text" required="" placeholder="Enter Course Code" name="ccode"></td></tr>
                <tr><td>Action:</td><td><select name="action">
                            <option>Update</option>
                            <option>Delete</option>
                        </select></td></tr>
            </table><br>
            <input type="submit" value="Continue">
            <input type="reset" value="Reset">
            </center>
   </form>
<button></button><a href='/Assignment/crudPage.jsp'><button>Previous</button></a>
</body>
</html>