import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class validateLogin extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM login WHERE username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            request.setAttribute("user",username);
            if(rs.next()){
                RequestDispatcher rd=request.getRequestDispatcher("/homepage.html");
                rd.forward(request,response);
            }
            else{
                writer.println("<h1>Invalid Login<h1><a href='/library/login.html'><input type='submit' value='Go Back'></a>");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }
    }
}

