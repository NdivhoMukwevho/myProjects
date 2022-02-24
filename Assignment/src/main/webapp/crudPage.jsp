<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Available Students</title>
<style type="text/css">
            table.data, table.data th, table.data td {
                border: 1px solid black;
            }
            table.data {
            border-collapse: collapse;
            border: 1px solid black;
            width: 50%;
            }
            table.data th {
                height: 50px;
                text-align: left;
                background-color: #4CAF50;
                color: white;
            }
            
            table.data td {
                height: 30px;
                vertical-align: bottom;
            }
            table.data th, table.data td {
                padding: 15px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            button{
                background-color: #ffcccc;
                border: 1;
                height: 35px;
                hover{background-color: #33ff33}
            }
</style>
</head>
<body>
<h3>Upload Successful</h3>
<%
	Class.forName("com.mysql.jdbc.Driver");
	Connection con=DriverManager.getConnection(  
	"jdbc:mysql://localhost:3306/school","root","ndivho"); 
	
	HttpSession ds = request.getSession();
	String dups = (String)ds.getAttribute("duplicates");
%>
<p><%=dups %></p>
<table style="border-collapse: collapse; width: 100%;" border="1" class="data">
<tbody>
<tr>
    <th>Student Number</th>
    <th>Firstname</th>
    <th>Surname</th>
    <th>CourseCode</th>
    <th>Course Description</th>
    <th>Grade</th>
  </tr>
<% PreparedStatement pr = con.prepareStatement("select * from student");
   ResultSet rs = pr.executeQuery();
   while(rs.next()){ %>
<tr>
<td><%=rs.getInt(1) %></td>
<td><%=rs.getString(2) %></td>
<td><%=rs.getString(3) %></td>
<td><%=rs.getString(4) %></td>
<td><%=rs.getString(5) %></td>
<td><%=rs.getString(6) %></td>
</tr>
<%} %>
</tbody>
</table><br>
<a href='/Assignment/updateDelete.jsp'><button>Update/Delete</button></a>
<a href='/Assignment/addStud.jsp'><button>Add New</button></a>
<a href='/Assignment/upload.jsp'><button>Previous</button></a>
</body>
</html>