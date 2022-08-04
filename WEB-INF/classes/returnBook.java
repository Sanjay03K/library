import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class returnBook extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM purchase WHERE username = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            List<String> los= new ArrayList<String>();
            while (rs.next())
            {    
                String id = rs.getString("s_no");      
                String username = rs.getString("username");  
                String book_id = rs.getString("book_id");  
                String date = rs.getString("purchased_date");
                String val = "{\"id\" : \""+id+"\",\"username\" :\""+username+"\",\"book_id\" : \""+book_id+"\",\"date\" : \""+date+"\"}";
                los.add(val);
            }
            writer.println(los);
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }
    }
}