

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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class updateServ
 */
@WebServlet("/updateServ")
public class updateServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateServ() {
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
		String actualStdNum = request.getParameter("actstdnum");
		String actualCode = request.getParameter("actcode");
		PrintWriter wr = response.getWriter();
		
		try {
			Connection con = createConnection();
            
			HttpSession ds = request.getSession();
			
			if(stdNum.trim().equals(actualStdNum.trim()) && cCode.trim().equals(actualCode.trim())){
				//if there was no change in keys just update
				doUpdate(con, stdNum, cCode, cDesc, firstName,
						 surname, grade, actualStdNum, actualCode);
				ds.setAttribute("duplicates", "record updated successfully");
				RequestDispatcher rd = request.getRequestDispatcher("/crudPage.jsp");
				rd.forward(request, response);
			}else {//if there was change in keys check if the new key doesnt exist
				PreparedStatement reqs = con.prepareStatement("select ccode from student where stdnum=? and ccode=?");
				reqs.setInt(1, Integer.parseInt(stdNum));
				reqs.setString(2, cCode);
				ResultSet rsReqs = reqs.executeQuery();

				if (rsReqs.next()) {//if it does show error
					wr.write("Student number and Course Code combination already exists for ("+stdNum+" "+cCode+")");
				} else {//if it doesnt update
					doUpdate(con, stdNum, cCode, cDesc, firstName,
						 surname, grade, actualStdNum, actualCode);
					ds.setAttribute("duplicates", "record updated successfully");
					RequestDispatcher rd = request.getRequestDispatcher("/crudPage.jsp");
					rd.forward(request, response);
					
				}
			}
			

			// close resources
			wr.close();
		} catch (Exception e) {
			wr.print("The following error occurred <strong>" + e.getMessage()
					+ "</strong> Please take screenshot and send to IT");
		}
//		doGet(request, response);
	}
	private Connection createConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/school","root","ndivho"); 
		return con;
	}

	private void doUpdate(Connection con, String stdNum, String cCode, String cDesc, String firstName,
			String surname, String grade, String actualStdNum, String actualCode) throws Exception{
		PreparedStatement upd = con
				.prepareStatement("update student set STDNUM=?, FNAME=?, SNAME=?, CCODE=?, CDESC=?, GRADE=?"
						+ "where STDNUM=? and CCODE=?");
		upd.setInt(1, Integer.parseInt(stdNum));
		upd.setString(2, firstName);
		upd.setString(3, surname);
		upd.setString(4, cCode);
		upd.setString(5, cDesc);
		upd.setString(6, grade);
		upd.setInt(7, Integer.parseInt(actualStdNum));
		upd.setString(8, actualCode);
		upd.execute();
		upd.close();
	}
}
