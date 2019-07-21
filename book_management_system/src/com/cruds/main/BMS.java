package com.cruds.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cruds.db.BookDAO;
import com.cruds.model.Book;
import com.cruds.model.BookIssue;

public class BMS {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int choice;
		int id, numBooks;
		String isbn, usn, title, category, name, email, issue_date, return_date;
		
		Book book;
		List<Book> blist = new ArrayList<Book>();
		BookDAO dao = new BookDAO();
		Date idate = null, rdate = null;

		while(true) {
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
					
					System.out.print("Enter number of books : ");
					numBooks = sc.nextInt();

				
					if( dao.addBook(new Book(isbn, title, category, numBooks)) )
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

					System.out.print("Enter issue date : ");
					issue_date = sc.next();
					try {
						idate = new SimpleDateFormat("dd/MM/yyyy").parse(issue_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					System.out.print("Enter return date : ");
					return_date = sc.next();
					try {
						rdate = new SimpleDateFormat("dd/MM/yyyy").parse(issue_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					System.out.print("Enter book ISBN : ");
					isbn = sc.next();
					
					dao.issueBook(new BookIssue(usn, idate, rdate, isbn));
					
					break;
			
			case 7: System.out.print("Enter usn : ");
					usn = sc.next();
			
					if(!dao.listBookByUsn(usn))
					{
						System.out.println("No record found.");
					}
					break;
					
			case 8:	if(!dao.getBookToReturn())
					{
						System.out.println("There is no book to be returned today");
					}
					break;
					
			case 9: System.exit(0);
			}
			
		}
	
	}
}
