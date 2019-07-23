package com.cruds.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cruds.db.BookDAO;
import com.cruds.model.Author;
import com.cruds.model.Book;
import com.cruds.model.Issue;
import com.cruds.model.Student;

public class BMS {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int choice;
		int numBooks;
		String isbn, usn, title, category, name, email;
		
		List<Book> blist = new ArrayList<Book>();
		BookDAO dao = new BookDAO();
		
		Calendar cal = Calendar.getInstance();
		Date curDate = cal.getTime();
		
		

		while(true) {
			System.out.println("---- SIT LIBRARY ----");
			System.out.println("1.Add a book");
			System.out.println("2.Search book by title");
			System.out.println("3.Search book by category");
			System.out.println("4.Search book by Author");
			System.out.println("5.List all books along with author");
			System.out.println("6.Issue book to student");
			System.out.println("7.List books by usn");
			System.out.println("8.List books to be returned today");
			System.out.println("9.Exit");
			System.out.print("Enter your choice: ");
			choice = Integer.parseInt(sc.next());
			
			//blist.clear();
			switch(choice) {
			case 1: System.out.print("Enter isbn : ");
					isbn = sc.next();
					
					System.out.print("Enter title : ");
					title = sc.next();
					
					System.out.print("Enter category : ");
					category = sc.next();
					
					System.out.print("Enter Author name : ");
					name = sc.next();
					
					System.out.print("Enter Author mail id : ");
					email = sc.next();
					
					System.out.print("Enter number of books : ");
					numBooks = sc.nextInt();

				
					if( dao.addBook(new Book(isbn, title, category, numBooks)) && dao.addAuthor(new Author(name, email, isbn)) )
					{
						System.out.println("Book details added successfully");
					}
					else
					{
						System.out.println("Invalid Credentials!");
					}
					
					break;
			
			
			case 2: System.out.print("Enter title : ");
					title = sc.next();
					
					blist = dao.getByTitle(title);
					
					if(blist.size() != 0)
					{
						System.out.println("Book ISBN\tBook Title\tCategory\tNo of Books");
						for(Book b : blist)
						{
							System.out.println(b);
						}
					}
					else
					{
						System.out.println("No record found.");
					}
					break;
			
			case 3: System.out.print("Enter category : ");
					category = sc.next();

					blist = dao.getByCategory(category);

					if(blist.size() != 0)
					{
						System.out.println("Book ISBN\tBook Title\tCategory\tNo of Books");
						for(Book b : blist)
						{
							System.out.println(b);
						}
					}
					else
					{
						System.out.println("No book found for " + category + " category.");
					}
					break;
			
			case 4: System.out.print("Enter name : ");
					name = sc.next();
					
					blist = dao.getByAuthor(name);

					if(blist.size() != 0)
					{
						System.out.println("Book ISBN\tBook Title\tCategory\tNo of Books");
						for(Book b : blist)
						{
							System.out.println(b);
						}
					}
					else
					{
						System.out.println("No record found");
					}
					break;
					
			case 5: if(!dao.getBookAuthor())
					{
						System.out.println("No record found");
					}
					break;
					
			case 6: System.out.print("Enter usn : ");
					usn = sc.next();
					
					System.out.print("Enter name : ");
					name = sc.next();
										
					System.out.print("Enter book ISBN : ");
					isbn = sc.next();	

					Date issue_date = curDate;
					
					cal.add(Calendar.DATE, 7); //add 7 days to current date
					Date return_date = cal.getTime();
					
					//java.sql.Date idate = new java.sql.Date(issue_date.getTime());
					//java.sql.Date rdate = new java.sql.Date(return_date.getTime());
					
					
					if( (dao.studentExist(new Student(usn, name)) || dao.addStudent(new Student(usn, name))) && dao.issueBook(new Issue(usn, issue_date, return_date, isbn)) )
					{
						System.out.println("Book issued. Return date : " + return_date);
					}
					else
					{
						System.out.println("Can't issue.");
					}
					
					break;
			
			case 7: System.out.print("Enter usn : ");
					usn = sc.next();
			
					if(!dao.listBookByUsn(usn))
					{
						System.out.println("No record found.");
					}
					break;
					
			case 8:	if(!dao.getBookToReturn(curDate))
					{
						System.out.println("There is no book to be returned today");
					}
					break;
					
					
			case 9: System.exit(0);
			}
			
			System.out.println("\n");
			
		}
	
	}
}
