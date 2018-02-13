import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
   
    private Connection jdbcConnection;
  
   protected void connect() throws SQLException{
       if(jdbcConnection==null||jdbcConnection.isClosed()){
           try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            jdbcConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
       }
   }

public List<Book> listbookdao() throws SQLException {
    connect();
    List<Book> lt=new ArrayList<Book>();
    Statement stmt=jdbcConnection.createStatement();
    ResultSet rs=stmt.executeQuery("SELECT * FROM `book`");
    while(rs.next()){
        lt.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4)));
    }
    
    return lt;
}

public void insert(Book book) throws SQLException {
connect();

PreparedStatement ps=jdbcConnection.prepareStatement("INSERT INTO `book`(`title`, `author`, `price`) VALUES (?,?,?)");
ps.setString(1, book.getTitle());
ps.setString(2, book.getAuthor());
ps.setFloat(3, book.getPrice());
ps.executeUpdate();

}

public Book updatebook(Book book) throws SQLException {
    connect();
    Statement statement =jdbcConnection.createStatement();
    ResultSet rs=statement.executeQuery("SELECT * FROM `book` WHERE book_id="+book.id);
    Book b=new Book();
    while(rs.next()){
        b.id=rs.getInt(1);
        b.title=rs.getString(2);
        b.author=rs.getString(3);
        b.price=rs.getFloat(4);
    }
    
	return b;
}

public void updateFeild(Book book) throws SQLException {
   connect();
   
   PreparedStatement ps=jdbcConnection.prepareStatement("UPDATE `book` SET `title`=?,`author`=?,`price`=? WHERE book_id=?");
   ps.setString(1, book.getTitle());
   ps.setString(2, book.getAuthor());
   ps.setFloat(3, book.getPrice());
   ps.setInt(4, book.getId());
   boolean sa=ps.executeUpdate()>0;
   

}

public  void deletBook(Book book) throws SQLException {
    connect();
    PreparedStatement ps=jdbcConnection.prepareStatement("DELETE FROM `book` WHERE book_id=?");
    ps.setInt(1, book.getId());
    ps.executeUpdate();
}

}