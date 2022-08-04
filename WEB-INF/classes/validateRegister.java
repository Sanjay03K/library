import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class validateRegister extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String Repassword = request.getParameter("re-password");
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("INSERT INTO login(username,password) VALUES(?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            int m = ps.executeUpdate();
            if(m==1){
                writer.println("<h1>Registered Successfully<h1><a href='/library/login.html'><input type='submit' value='Go Back'></a>");
            }
            else{
                writer.println("<h1>Unable to create account<h1>");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }

    }
}

