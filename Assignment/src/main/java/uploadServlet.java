

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class uploadServlet
 */
@WebServlet("/uploadServlet")
@MultipartConfig
public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public uploadServlet() {
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
		String dups = "";
		Connection con = null;
		//retrieve the file attached from jsp
		Part myCsv = request.getPart("file");
		
		//get the file input stream that will be read from
	    InputStream csvStream = myCsv.getInputStream();
	    
	    PrintWriter writer = response.getWriter();
	    //read the input stream using buffered reader line by line to speed up the reading process
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {

            // read line by line
	    	boolean headerRead = false;
	        String csvLine = ""; 
            while ((csvLine = br.readLine()) != null) {
            	if(headerRead == false) {
            		//skip header, turn the indicator on and continue
            		headerRead = true;
            		con = createConnection();
            		continue;
            	}
            	else {
            		//process line
            		String[] record = csvLine.split(";");
            		if(!recordExist(con, record))//if doesnt exist then insert
            		    processLine(record, con);
            		else//keep record of duplicates
            			dups += record[0]+"+"+record[3]+", ";
            	}
            }
            br.close();
            //upload complete
            // move on to crudPage which will display all records
            HttpSession ds = request.getSession();
            if(!dups.trim().equals(""))
            	dups = "The following records were not uploaded as they already exist in the Database "+dups;
            ds.setAttribute("duplicates", dups);
            RequestDispatcher rd = request.getRequestDispatcher("/crudPage.jsp");
            rd.forward(request, response);
            con.close();
            csvStream.close();
            writer.close();
        }catch(Exception e) {
        	writer.print("The following error occurred <strong>"+ e.getMessage()+"</strong> Please take screenshot and send to IT");
        }
//		doGet(request, response);
	}
	private void processLine(String[] record, Connection myCon) throws Exception{
		PreparedStatement write = myCon.prepareStatement("insert into student values(?, ?, ?, ?, ?, ?)");
		write.setInt(1, Integer.parseInt(record[0]));
		write.setString(2, record[1]);
		write.setString(3, record[2]);
		write.setString(4, record[3]);
		write.setString(5, record[4]);
		write.setString(6, record[5]);
		write.execute();
	}
	private Connection createConnection() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/school","root","ndivho"); 
		return con;
	}
	
    private boolean recordExist(Connection con, String[] rec) throws Exception{
    	PreparedStatement prExist = con.prepareStatement("select CCODE from student where STDNUM=? and CCODE=?");
    	prExist.setInt(1, Integer.parseInt(rec[0]));
    	prExist.setString(2, rec[3]);
    	ResultSet rsExist = prExist.executeQuery();
    	boolean results =  rsExist.next();
    	prExist.close();
    	rsExist.close();
    	return results;
	}
}
