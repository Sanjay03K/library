import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class displayFine extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM purchase WHERE username=? AND fine!=?");
            ps.setString(1, username);
            ps.setString(2, "0");
            ResultSet rs = ps.executeQuery();
            List<String> los= new ArrayList<String>();
            while (rs.next()) 
            {          
                String s_no = rs.getString("s_no");  
                String name = rs.getString("username");  
                String book_name = rs.getString("book_id");  
                String fine = rs.getString("fine"); 
                String val = "{\"id\" : \""+s_no+"\",\"name\" :\""+name+"\",\"book_name\" : \""+book_name+"\",\"fine\" : \""+fine+"\"}";
                los.add(val);   
            } 
            writer.println(los);
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }
    }
}