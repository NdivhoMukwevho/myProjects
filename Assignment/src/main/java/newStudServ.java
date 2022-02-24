

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class newStudServ
 */
@WebServlet("/newStudServ")
public class newStudServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public newStudServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String stdNum = request.getParameter("stdnum");
		String cCode = request.getParameter("ccode");
		String cDesc = request.getParameter("cdesc");
		String firstName = request.getParameter("firstname");
		String surname = request.getParameter("surname");
		String grade = request.getParameter("grade");
		PrintWriter wr = response.getWriter();
		
		try {
			Connection con = createConnection();
			PreparedStatement reqs = con.prepareStatement("select ccode from student where stdnum=? and ccode=?");
			reqs.setInt(1, Integer.parseInt(stdNum));
			reqs.setString(2, cCode);
			ResultSet rsReqs = reqs.executeQuery();
			
			if(rsReqs.next()) {
				wr.write("Student number and Course Code combination already exists for ("+stdNum+" "+cCode+")");
			}else {
				PreparedStatement add = con.prepareStatement("insert into student values(?, ?, ?, ?, ?, ?)");
				add.setInt(1, Integer.parseInt(stdNum));
				add.setString(2, firstName);
				add.setString(3, surname);
				add.setString(4, cCode);
				add.setString(5, cDesc);
				add.setString(6, grade);
				add.execute();
				
				add.close();
				
				RequestDispatcher rd = request.getRequestDispatcher("/crudPage.jsp");
	            rd.forward(request, response);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			wr.print("The following error occurred <strong>"+ e.getMessage()+"</strong> Please take screenshot and send to IT");
		}
//		doGet(request, response);
	}
	private Connection createConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/school","root","ndivho"); 
		return con;
	}

}
