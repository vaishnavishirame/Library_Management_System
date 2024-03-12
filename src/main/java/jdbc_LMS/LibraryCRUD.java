package jdbc_LMS;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LibraryCRUD {
	
	public Connection getConnection() throws Exception {
		
		String className = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lmsdb";
		String user = "root";
		String pass = "root";
		
		Class.forName(className);
		Connection connection = DriverManager.getConnection(url, user, pass);
		
		return connection;
	}
	
	public int signUpUser(User user) throws Exception
	{
		Connection connection = getConnection() ;
		
		PreparedStatement preparedStatement = connection.prepareStatement("insert into user(id, name, phone, email, password) values(?, ?, ?, ?, ?)");
		
		preparedStatement.setInt(1, user.getId());
		preparedStatement.setString(2, user.getName());
		preparedStatement.setLong(3, user.getPhone());
		preparedStatement.setString(4, user.getEmail());
		preparedStatement.setString(5, user.getPassword());
		
		int result = preparedStatement.executeUpdate();
		
		return result ;
	}
	
	public int logInUser(String email, String password, User user) throws Exception
	{
		Connection connection = getConnection() ;
		
		String sql = "SELECT * FROM USER WHERE EMAIL=? AND PASSWORD=?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		
		preparedStatement.setString(1, email);
		preparedStatement.setString(2, password) ;
		
		ResultSet result = preparedStatement.executeQuery() ;
		
		String dbEmail = null ;
		String dbPassword = null ;
		
		while(result.next())
		{
			dbEmail = result.getString("email") ;
			dbPassword = result.getString("password") ;
			
			if(dbEmail.equals(email) || dbPassword.equals(password)) {
				break ;
			}
		}
		
		if(email.equals(dbEmail))
		{
			if(password.equals(dbPassword)) {
				System.out.println("Login Successfully");
				connection.close() ;
				return 1 ;
			}
			else {
				System.out.println("Incorrect Password");
				connection.close() ;
				return 0 ;
			}
		}
		else {
//			System.out.println("Not Registered");
			connection.close() ;
			return 0 ;
		}
	}
	
	public int logInUserViaMobile(long number, String password, User user) throws Exception
	{
		Connection connection = getConnection() ;
		
		String sql = "SELECT * FROM USER WHERE PHONE=? AND PASSWORD=?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		
		preparedStatement.setLong(1, number);
		preparedStatement.setString(2, password) ;
		
		ResultSet result = preparedStatement.executeQuery() ;
		
		long dbNumber = 0l ;
		String dbPassword = null ;
		
		while(result.next())
		{
			dbNumber = result.getLong("phone") ;
			dbPassword = result.getString("password") ;
			
			if((dbNumber == number) || dbPassword.equals(password)) {
				break ;
			}
		}
		
		if(number == dbNumber)
		{
			if(password.equals(dbPassword)) {
				System.out.println("Login Successfully");
				connection.close() ;
				return 1 ;
			}
			else {
				System.out.println("Incorrect Password");
				connection.close() ;
				return 0 ;
			}
		}
		else {
//			System.out.println("Not Registered");
			connection.close() ;
			return 0 ;
		}
	}
	
	public void getAllBooks() throws Exception
	{
		Connection connection = getConnection() ;
		
		String sql = "SELECT * from BOOK" ;
		
		//Step 3 : Create prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		
		ResultSet result = preparedStatement.executeQuery() ;
		
		
		while (result.next()) {
		    String column1 = result.getString("id");
		    String column2 = result.getString("name");
		    String column3 = result.getString("author");
		    String column4 = result.getString("price");
		    String column5 = result.getString("genre");

		    // Print the values
		    System.out.println("id : " + column1 + ", name : " + column2 +", author : "+column3+", price : "+column4+", genre : "+column5);
		}

		
		//Closing the PreparedStatement class
		preparedStatement.close();
	}

	public int addBooks(Book book) throws Exception
	{
		Connection connection = getConnection() ;
		
		String sql = "INSERT INTO BOOK VALUES(?, ?, ?, ?, ?)" ;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		
		//Inserting query dynamically
		preparedStatement.setInt(1, book.getId()) ;
		preparedStatement.setString(2, book.getName()) ; 
		preparedStatement.setString(3, book.getAuthor()) ;
		preparedStatement.setDouble(4, book.getPrice()) ;
		preparedStatement.setString(5, book.getGenre()) ;
		
		int result = preparedStatement.executeUpdate() ;
		
		//Closing the PreparedStatement class
		preparedStatement.close();
		
		return result ;
	}
	
	public int deleteBook(int id) throws Exception
	{
		Connection connection = getConnection() ;
		
		String sql = "DELETE FROM BOOK WHERE ID = ?" ;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		
		//Inserting query dynamically
		preparedStatement.setInt(1, id);
		
		int result = preparedStatement.executeUpdate() ;
		
		//Closing the PreparedStatement class
		preparedStatement.close();
		
		return result ;
		
	}
	
	public int updateBook(int id, Book book) throws Exception
	{
		Connection connection = getConnection() ;
		
		String sql = "UPDATE BOOK SET NAME = ?,AUTHOR=?, PRICE=?, GENRE=? WHERE ID = ?" ;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		preparedStatement.setString(1, book.getName());
		preparedStatement.setString(2, book.getAuthor());
		preparedStatement.setDouble(3, book.getPrice());
		preparedStatement.setString(4, book.getGenre());
		preparedStatement.setInt(5, id);
		
		
		int result = preparedStatement.executeUpdate() ;
		
		preparedStatement.close();
		
		return result ;
	}
	
	public void getBookByid(int id) throws Exception
	{
		String sql = "SELECT * FROM BOOK WHERE ID=?" ;
		
		Connection connection = getConnection() ;
		
		
		
		//Step 3 : Create prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
				
		preparedStatement.setInt(1, id);
				
		ResultSet result = preparedStatement.executeQuery() ;
				
		while (result.next()) {
			String column1 = result.getString("id");
			String column2 = result.getString("name");
			String column3 = result.getString("author");
			String column4 = result.getString("price");
			String column5 = result.getString("genre");

			// Print the values
			System.out.println("id : " + column1 + ", name : " + column2 +", author : "+column3+", price : "+column4+", genre : "+column5);
		}
				
		//Closing the PreparedStatement class
		preparedStatement.close();
	}
	
	public void getBookByName(String name) throws Exception
	{
		String sql = "SELECT * FROM BOOK WHERE NAME=?" ;
		
		Connection connection = getConnection() ;
		
		
		
		//Step 3 : Create prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
				
		preparedStatement.setString(1, name);
				
		ResultSet result = preparedStatement.executeQuery() ;
				
		while (result.next()) {
			String column1 = result.getString("id");
			String column2 = result.getString("name");
			String column3 = result.getString("author");
			String column4 = result.getString("price");
			String column5 = result.getString("genre");

			// Print the values
			System.out.println("id : " + column1 + ", name : " + column2 +", author : "+column3+", price : "+column4+", genre : "+column5);
		}
				
		//Closing the PreparedStatement class
		preparedStatement.close();
	}
	
	public void getBookByAuthor(String authorName) throws Exception
	{
		String sql = "SELECT * FROM BOOK WHERE AUTHOR=?" ;
		
		Connection connection = getConnection() ;
		
		//Step 3 : Create prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
				
		preparedStatement.setString(1, authorName);
				
		ResultSet result = preparedStatement.executeQuery() ;
				
		while (result.next()) {
			String column1 = result.getString("id");
			String column2 = result.getString("name");
			String column3 = result.getString("author");
			String column4 = result.getString("price");
			String column5 = result.getString("genre");

			// Print the values
			System.out.println("id : " + column1 + ", name : " + column2 +", author : "+column3+", price : "+column4+", genre : "+column5);
		}
				
		//Closing the PreparedStatement class
		preparedStatement.close();
	}
	
	public void getBookByGenre(String genre) throws Exception
	{
		String sql = "SELECT * FROM BOOK WHERE GENRE=?" ;
		
		Connection connection = getConnection() ;
		
		//Step 3 : Create prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
				
		preparedStatement.setString(1, genre);
				
		ResultSet result = preparedStatement.executeQuery() ;
				
		while (result.next()) {
			String column1 = result.getString("id");
			String column2 = result.getString("name");
			String column3 = result.getString("author");
			String column4 = result.getString("price");
			String column5 = result.getString("genre");

			// Print the values
			System.out.println("id : " + column1 + ", name : " + column2 +", author : "+column3+", price : "+column4+", genre : "+column5);
		}
				
		//Closing the PreparedStatement class
		preparedStatement.close();
	}
	
	public void getUserById(int id) throws Exception
	{
		String sql = "SELECT * FROM USER WHERE ID=?" ;
		
		Connection connection = getConnection() ;
		
		//Step 3 : Create prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
				
		preparedStatement.setInt(1, id);
				
		ResultSet result = preparedStatement.executeQuery() ;
				
		while (result.next()) {
			String column1 = result.getString("id");
			String column2 = result.getString("name");
			String column3 = result.getString("phone");
			String column4 = result.getString("email");
			String column5 = result.getString("password");

			// Print the values
			System.out.println("id : " + column1 + ", name : " + column2 +", phone : "+column3+", email : "+column4+", password : "+column5);
		}
				
		//Closing the PreparedStatement class
		preparedStatement.close();
	}
	
	public int updateUser(int id, User user) throws Exception
	{
		Connection connection = getConnection() ;
		
		String sql = "UPDATE USER SET NAME = ?,PHONE=?, EMAIL=?, PASSWORD=? WHERE ID = ?" ;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		preparedStatement.setString(1, user.getName());
		preparedStatement.setLong(2, user.getPhone());
		preparedStatement.setString(3, user.getEmail());
		preparedStatement.setString(4, user.getPassword());
		preparedStatement.setInt(5, id);
		
		
		int result = preparedStatement.executeUpdate() ;
		
		preparedStatement.close();
		
		return result ;
	}

}