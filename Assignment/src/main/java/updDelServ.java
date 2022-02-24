

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
 * Servlet implementation class updDelServ
 */
@WebServlet("/updDelServ")
public class updDelServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updDelServ() {
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
		String action = request.getParameter("action");
		PrintWriter wr = response.getWriter();
		
		try {
			Connection con = createConnection();
			
			PreparedStatement reqs = con.prepareStatement("select ccode from student where stdnum=? and ccode=?");
			reqs.setInt(1, Integer.parseInt(stdNum));
			reqs.setString(2, cCode);
			ResultSet rsReqs = reqs.executeQuery();
			// check if the student exist first
			if (rsReqs.next()) {
				if(action.contains("Update")) {
					PreparedStatement pr = con.prepareStatement("select * from student where STDNUM = ? and CCODE = ?");
					pr.setInt(1, Integer.parseInt(stdNum));
					pr.setString(2, cCode);
					ResultSet rs = pr.executeQuery();
					if(rs.next()) {
						HttpSession hs = request.getSession();
						hs.setAttribute("updStud",  rs);
						RequestDispatcher rd = request.getRequestDispatcher("/update.jsp");
		                rd.forward(request, response);
					}
					pr.close();
					rs.close();
				}else {
					PreparedStatement delStud = con.prepareStatement("delete from student where STDNUM=? and CCODE=?");
					delStud.setInt(1, Integer.parseInt(stdNum));
					delStud.setString(2, cCode);
					delStud.execute();
					HttpSession ds = request.getSession();
					ds.setAttribute("duplicates", "record deleted successfully");
					RequestDispatcher rd = request.getRequestDispatcher("/crudPage.jsp");
	                rd.forward(request, response);
				}
			}else {
				wr.print(stdNum+" "+cCode+" does not exist please enter a valid combination");
			}
			wr.close();
			con.close();
			reqs.close();
			rsReqs.close();
		}catch (Exception e) {
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
