import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class fineInsert extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fine = request.getParameter("fine");
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("UPDATE purchase SET fine = ? WHERE book_id = ? AND username = ?");
            ps.setString(1, fine);
            ps.setString(2, id);
            ps.setString(3, username);
            int m = ps.executeUpdate();            
            if(m==1){
                writer.println("Pay the fine of Rs " +fine+ " to submit this book.");
            }
            else{
                writer.println("Unable to update fine. please try again.");
            }  
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }
    }
}