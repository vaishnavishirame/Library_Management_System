package jdbc_LMS;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LibraryController {
	public static void main(String[] args) throws Exception  {
		
		Scanner scanner = new Scanner(System.in) ;
		
		User user = new User() ;
		Book book = new Book() ;
		LibraryCRUD crud = new LibraryCRUD() ;
		
		System.out.println("**********************************************************************************") ;
		System.out.println("\t\tWelcome to Library Management System");
		System.out.println("*********************************************************************************") ;
		
		System.out.println("Enter your choice\n1. SignUp\n2. LogIn\n3. Exit");
		int choice1 = scanner.nextInt() ;
		
		switch(choice1)
		{
			case 1 :
			{
				userSignUp(user, book, crud);
				
			}
			break ;
			case 2 :
			{
				System.out.println("\n\n***************************************************************************") ;
				System.out.println("\t\tWelcome to Log In Page");
				System.out.println("\n***************************************************************************") ;
				System.out.println("Enter your choice\nPress E/e for login via email\nPress M/m for login via mobile");
				char choice2 = scanner.next().charAt(0) ;
				if(choice2=='E'||choice2=='e')
				{
					System.out.print("Enter your email : ");
					String email = scanner.next() ;
					System.out.print("Enter your password : ");
					String password = scanner.next() ;
					
					int res = crud.logInUser(email, password, user);
					
					boolean flag1 = true ;
					while(flag1)
					{
						flag1 =repeatedTask(res, crud, user, book,flag1) ;
					}
					
				}
				else if(choice2=='M'||choice2=='m')
				{
					System.out.print("Enter your phone number : ");
					long phone1 = scanner.nextLong() ;
					System.out.print("Enter your password : ");
					String password = scanner.next() ;
					
					int res = crud.logInUserViaMobile(phone1, password, user);
					
					boolean flag2 = true ;
					while(flag2)
					{
						flag2 =repeatedTask(res, crud, user, book,flag2) ;
					}
					
					
				}
				else {
					System.out.println("You have entered wrong key");
				}
				
			}
			break ;
			case 3 :
			{
				System.exit(0) ;
			}
			break ;
			default :
			{
				System.out.println("You haver entered wrong key...!!!");
			}
		}
		
		
	}
	
	public static void userSignUp(User user, Book book, LibraryCRUD crud) throws Exception
	{
		Scanner scanner = new Scanner(System.in) ;
		
		System.out.println("\n\n---------------------------------------------------------------------------") ;
		System.out.println("\t\t\tWelcome to Sign Up Page");
		System.out.println("---------------------------------------------------------------------------") ;
		
		System.out.print("Enter your id here : ") ;
		int id = scanner.nextInt() ;
		System.out.print("Enter your name : ") ;
		String name = scanner.next() ;
		System.out.print("Enter your phone number : ") ;
		long phone = scanner.nextLong() ;
		
		String email = "" ;
		
		
		String lastCheck = "@gmail.com" ; 
		boolean flag = true ;
		while(flag) {
			System.out.print("Enter your email id : ") ;
			email = scanner.next() ;
			int size = email.length() ;
			String check = email.substring(size-10, size) ;
			
			if(check.equals(lastCheck))
			{
				flag = false ;
			}
			else {
				System.out.println("'@gmail.com' must be at the end");
			}
		}
		
		System.out.print("Enter your password : ") ;
		String password = scanner.next() ;
		
		user.setId(id) ;
		user.setName(name) ;
		user.setPhone(phone) ;
		user.setEmail(email) ;
		user.setPassword(password) ;
		
		int result = crud.signUpUser(user) ;
		
		if (result != 0) {
			System.out.println("Inserted!");
		} else {
			System.out.println("Not Inserted!");
		}
	}
	
	public static void addBook(User user, Book book, LibraryCRUD crud) throws Exception
	{
		Scanner scanner = new Scanner(System.in) ;
		
		System.out.print("Enter the book id : ") ;
		int bookId = scanner.nextInt() ;
		System.out.print("Enter the name of the book : ") ;
		String bookName = scanner.next() ;
		System.out.println("Enter the author of the book : ") ;
		String bookAuthor = scanner.next() ;
		System.out.println("Enter the price of the book : ") ;
		double book_price = scanner.nextDouble() ;
		System.out.println("Enter the genre of the book : ");
		String book_genre = scanner.next() ;
		
		book.setId(bookId) ;
		book.setName(bookName) ;
		book.setAuthor(bookAuthor) ;
		book.setPrice(book_price);
		book.setGenre(book_genre);
		
		int result = crud.addBooks(book);
		//validating the Insertion
		if(result != 0)
		{
			System.out.println("Inserted");
		}
		else 
		{
			System.out.println("Not Inserted");
		}
	}
	
	public static void deleteBook(int id, LibraryCRUD crud) throws Exception 
	{
		int result = crud.deleteBook(id) ;
		
		//validating the Insertion
		if(result != 0)
		{
			System.out.println("Deleted");
		}
		else 
		{
			System.out.println("Not Deleted");
		}
	}
	
	public static void updateBook(Book book, int id, LibraryCRUD crud) throws Exception
	{
		Scanner scanner = new Scanner(System.in) ;
		crud.getBookByid(id);
		
		System.out.print("\nEnter the name of a book : ");
		String name1 = scanner.next() ;
		System.out.print("Enter the author name : ") ;
		String author1 = scanner.next() ;
		System.out.print("Enter the price of the book : ");
		double price1 = scanner.nextDouble() ;
		System.out.print("Enter the genre of the book : ") ;
		String genre1 = scanner.next() ;
		
		book.setId(id);
		book.setName(name1);
		book.setAuthor(author1);
		book.setPrice(price1);
		book.setGenre(genre1);
		
		int result = crud.updateBook(id, book);
		
		//validating the Insertion
		if(result != 0)
		{
			System.out.println("Updated");
		}
		else 
		{
			System.out.println("Not Updated");
		}
		
	}
	
	public static void getBookById(int id, LibraryCRUD crud) throws Exception
	{
		crud.getBookByid(id) ;
	}
	
	public static void getBookByName(String name, LibraryCRUD crud) throws Exception
	{
		crud.getBookByName(name);
	}
	
	public static void getBookByAuthor(String authorName, LibraryCRUD crud) throws Exception
	{
		crud.getBookByAuthor(authorName);
	}
	
	public static void getBookByGenre(String genre, LibraryCRUD crud) throws Exception
	{
		crud.getBookByAuthor(genre);
	}
	
	public static void getUserById(int id, LibraryCRUD crud, User user) throws Exception 
	{
		Scanner scanner = new Scanner(System.in) ;
		crud.getUserById(id);
		
		System.out.print("\nEnter the name of the user : ");
		String name1 = scanner.next() ;
		System.out.print("Enter the phone number : ") ;
		long phone1 = scanner.nextLong() ;
		System.out.print("Enter the new email of the user : ");
		String email1 = scanner.next() ;
		
		String password1 = "" ;
		
		boolean flag = true ;
		while(flag)
		{
			System.out.print("Do you want to update your password (Y/N) : ");
			char ch1 = scanner.next().charAt(0) ;
			if(ch1=='Y'||ch1=='y') {
				System.out.print("Enter the new password of the user : ") ;
				password1 = scanner.next() ;
				flag = false ;
			}
			else if(ch1=='N'||ch1=='n') {
				password1 = getPassword() ;
				flag = false ;
			}
			else {
				System.out.println("You have entered wrong key....Try again\n");
			}
		}
		
		user.setId(id);
		user.setName(name1);
		user.setPhone(phone1);
		user.setEmail(email1);
		user.setPassword(password1);
		
		int result = crud.updateUser(id, user) ;
		
		//validating the Insertion
		if(result != 0)
		{
			System.out.println("Updated");
		}
		else 
		{
			System.out.println("Not Updated");
		}
	}
	
	public static String getPassword() throws Exception
	{
		Scanner scanner = new Scanner(System.in) ;
		
		String className = "com.mysql.cj.jdbc.Driver" ;
		String url = "jdbc:mysql://localhost:3306/userdb" ;
		String user = "root" ;
		String pass = "root" ;
		String sql = "SELECT password FROM USER" ;
		
		//Step 1 : Load or Register the Driver
		Class.forName(className) ;
		
		//Step 2 : Establish the connection
		Connection connection = DriverManager.getConnection(url, user, pass) ;
		
		//Step 3 : Create prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
		
		
		ResultSet result = preparedStatement.executeQuery() ;
		
		String password = "" ;
		
		while (result.next()) {
		    // Assuming you have columns named "column1", "column2", etc.
		    // You can change these column names based on your actual schema
		    password = result.getString("password");
		    
		    return password ;
		}

		
		scanner.close();
		
		//Closing the PreparedStatement class
		preparedStatement.close();
		
		return password ;
	}
	
	public static boolean repeatedTask(int res, LibraryCRUD crud, User user, Book book,boolean flag) throws Exception
	{
		Scanner scanner = new Scanner(System.in) ;
		
		if(res==1)
		{
			System.out.println("\n\nEnter your choice\n1. Get all Books\n2. Add book\n3. Delete\n4. Update\n5. Get Book By Id\n6. Get book by name\n7. Get book by Author\n8. Get book by genre\n9. Update User detail\n10. LogOut");
			int choice3 = scanner.nextInt() ;
			switch(choice3)
			{
				case 1 : 
				{
					System.out.println("\n\nGet all books\n");
					crud.getAllBooks() ;
				}
				break ;
				case 2 : 
				{
					System.out.println("\n\nAdd Books\n");
					addBook(user, book, crud) ;
				}
				break ;
				case 3 : 
				{
					System.out.println("\n\nDelete book by id\n");
					System.out.print("Enter your id : ");
					int id = scanner.nextInt() ;
					deleteBook(id, crud) ;
				}
				break ;
				case 4 : 
				{
					System.out.println("\n\nUpdate book by id\n");
					System.out.print("Enter your id : ");
					int id = scanner.nextInt() ;
					updateBook(book, id, crud) ;
				}
				break ;
				case 5 : 
				{
					System.out.println("\n\nGet Book By Id\n");
					System.out.print("Enter your id : ");
					int id = scanner.nextInt() ;
					getBookById(id,crud) ;
				}
				break ;
				case 6 : 
				{
					System.out.println("\n\nGet Book By Name\n");
					System.out.print("Enter your Name : ");
					String name = scanner.next() ;
					getBookByName(name,crud) ;
				}
				break ;
				case 7 : 
				{
					System.out.println("\n\nGet Book By Author\n");
					System.out.print("Enter your Author Name : ");
					String author = scanner.next() ;
					getBookByAuthor(author,crud) ;
				}
				break ;
				case 8 : 
				{
					System.out.println("\n\nGet Book By Genre\n");
					System.out.print("Enter the genre : ");
					String genre = scanner.next() ;
					getBookByAuthor(genre,crud) ;
				}
				break ;
				case 9 : 
				{
					System.out.println("\n\nUpdate User Details\n");
					System.out.print("Enter the id to get details : ");
					int id = scanner.nextInt() ;
					
					getUserById(id, crud, user) ;
					
					
				}
				break ;
				case 10 : 
				{
					System.out.println("You are successfully Log out\nThank you for using") ;
					System.exit(0) ;
				}
				break ;
				default : {
					System.out.println("You have entered wrong key");
				}
			}
		}
		else {
			System.out.println("Login Unsuccessful");
			flag = false;
		}
		return flag;
	}
}
