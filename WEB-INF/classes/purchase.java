import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;

public class purchase extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM purchase WHERE book_id = ? AND username = ?");
            ps.setString(1, id);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                writer.println("You cannot purchase more than one count of a particular book.");
            }
            else{
                ps = con.prepareStatement("SELECT * FROM books WHERE book_name = ?");
                ps.setString(1, id);
                rs = ps.executeQuery();
                while (rs.next()) 
                {          
                    String avail = rs.getString("availability");   
                    int count = Integer.parseInt(avail);  
                    count = count-1;
                    String s=Integer.toString(count);
                    ps = con.prepareStatement("UPDATE books SET availability = ? WHERE book_name = ?");
                    ps.setString(1, s);
                    ps.setString(2, id);
                    int m = ps.executeUpdate();
                    if(m==1){
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        LocalDateTime now = LocalDateTime.now();
                        ps = con.prepareStatement("INSERT INTO purchase(username,book_id,purchased_date,fine) VALUES(?,?,?,0)");
                        ps.setString(1, username);
                        ps.setString(2, id);
                        ps.setString(3, dtf.format(now));
                        int n = ps.executeUpdate();
                        if(n==1){
                            writer.println("Purchased successfully on "+dtf.format(now));
                        }
                        else{
                            writer.println("Purchased success. Data updation failed.");
                        }
                    }
                    else{
                        writer.println("Purchased failed. please try again.");
                    }
                }
            } 
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }
    }
}