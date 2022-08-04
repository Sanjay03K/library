import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class getBooks extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM books");
            ResultSet rs = ps.executeQuery();
            List<String> los= new ArrayList<String>();
            while (rs.next()) 
            {          
                String id = rs.getString("book_id");  
                String name = rs.getString("book_name");  
                String author = rs.getString("author_name");  
                String description = rs.getString("book_description");  
                String availability = rs.getString("availability"); 
                String val = "{\"id\" : \""+id+"\",\"name\" :\""+name+"\",\"author\" : \""+author+"\",\"description\" : \""+description+"\",\"availability\" : \""+availability+"\"}";
                los.add(val);   
            } 
            writer.println(los);
        } catch (Exception e2) {
            e2.printStackTrace();
            writer.println(e2);
        }
    }
}
