import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
@WebServlet("/")
public class BookServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
}
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   String action=req.getServletPath();
   //PrintWriter out=resp.getWriter();
   try{
   switch(action){
       case "/insert":
     //           out.println("case 1");
                
                insertbook(req,resp);
                 break;
       case "/edit":
               System.out.println("sample");
               updatebook(req,resp);
               break;
        default:
       
                listbook(req,resp);
                break;

   }
}
catch(SQLException e){
    e.printStackTrace();
}
}
private void updatebook(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
    Book book=new Book(Integer.parseInt(req.getParameter("id")));
   BookDAO bookDAO=new BookDAO();
   Book refbook=bookDAO.updatebook(book);
   List<Book> al=new ArrayList<Book>();
   al.add(refbook);
   Gson  gson=new Gson();
   JsonElement ele=gson.toJsonTree(al,new TypeToken<List<Book>>(){}.getType());
   JsonArray j=ele.getAsJsonArray();
   resp.setContentType("application/json");
   System.out.println(j);
   resp.getWriter().print(j);


}
private void insertbook(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
    String title=req.getParameter("title");
    String author=req.getParameter("author");
    Float price=Float.parseFloat(req.getParameter("price"));
    Book book=new Book(title,author,price);
    BookDAO bookDAO=new BookDAO();
    bookDAO.insert(book);
    resp.sendRedirect("/");

}
private void listbook(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
    BookDAO bookdao=new BookDAO();
    List<Book> listBook=bookdao.listbookdao();
    Gson gson=new Gson();
    JsonElement element=gson.toJsonTree(listBook,new TypeToken<List<Book>>() {}.getType());
    JsonArray jsonArray=element.getAsJsonArray();
    resp.setContentType("application/json");
    System.out.println(jsonArray);
    resp.getWriter().print(jsonArray);
}
}