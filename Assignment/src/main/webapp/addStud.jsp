<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Capture</title>
</head>
<body>
<form method="post" action="newStudServ">
            <center>
            <br>
            Capture new Student<br><br>
            <table>
                <th></th><th></th>
                <tr><td>Student Number:</td><td><input type="number" required="" placeholder="Enter Student Number" name="stdnum"></td></tr>
                <tr><td>Firstname:</td><td><input type="text" required="" placeholder="Enter Firstname" name="firstname"></td></tr>
                <tr><td>Surname:</td><td><input type="text" required="" placeholder="Enter Surname" name="surname"></td></tr>
                <tr><td>Course Code:</td><td><input type="text" required="" placeholder="Enter Course Code" name="ccode"></td></tr>
                <tr><td>Course Description:</td><td><input type="text" required="" placeholder="Enter Course Description" name="cdesc"></td></tr>
                <tr><td>Grade:</td><td><input type="text" required="" placeholder="Enter Grade" name="grade"></td></tr>
            </table><br>
            <input type="submit" value="Capture">
            </center>
</form>
<button></button><a href='/Assignment/crudPage.jsp'><button>Previous</button></a>
</body>
</html>