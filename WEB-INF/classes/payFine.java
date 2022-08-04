import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class payFine extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String id = request.getParameter("book");
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("DELETE FROM purchase WHERE book_id = ? AND username = ?");
            ps.setString(1, id);
            ps.setString(2, username);
            int m = ps.executeUpdate(); 
            if(m==1){
                ps = con.prepareStatement("SELECT * FROM books WHERE book_name = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    String avail = rs.getString("availability");
                    int count = Integer.parseInt(avail);  
                    count = count+1;
                    String s=Integer.toString(count);
                    ps = con.prepareStatement("UPDATE books SET availability = ? WHERE book_name = ?");
                    ps.setString(1, s);
                    ps.setString(2, id);
                    int n = ps.executeUpdate();
                    if(n==1){
                        writer.println("Fine paid and book submitted successfully");
                    }
                    else{
                        writer.println("Fine paid. Data updation failed.");
                    }
                }
            }
            else{
                writer.println("Unable to pay fine. please try again.");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }
    }
}