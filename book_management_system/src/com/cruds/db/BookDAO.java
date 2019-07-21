package com.cruds.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cruds.model.Author;
import com.cruds.model.Book;
import com.cruds.model.BookIssue;

public class BookDAO {
	
	public boolean addBook(Book book)
	{
		String sql = "insert into book(book_isbn, book_title, category, no_of_books) values(?, ?, ?, ?)";
		int rows = 0;
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, book.getIsbn());
			ps.setString(2,  book.getTitle());
			ps.setString(3, book.getCategory());
			ps.setInt(4,  book.getQuantity());
			
			rows = ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rows > 0;
	}
	
	public List<Book> getByTitle(String title)
	{
		String sql = "select book_isbn, book_title, category, no_of_books from book where LOWER(book_title) like ? ";
		Book b = null;
		ArrayList<Book> blist = new ArrayList<>();
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + title.toLowerCase() + "%");
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				blist.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return blist;
	}
	
	public List<Book> getByCategory(String category)
	{
		String sql = "select book_isbn, book_title, category, no_of_books from book where LOWER(category) = ?";
		Book b = null;
		ArrayList<Book> blist = new ArrayList<>();
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, category.toLowerCase());
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				blist.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return blist;
	}
	
	public List<Book> getByAuthor(String name)
	{
		String sql = "select b.book_isbn, b.book_title, b.category, b.no_of_books from book b, author a where b.book_isbn = a.book_isbn and LOWER(a.author_name) = ?";
		Book b = null;
		ArrayList<Book> blist = new ArrayList<>();
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name.toLowerCase());
			
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				blist.add(b);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return blist;
	}
	
	public boolean getBookAuthor()
	{
		String sql = "select b.book_isbn, b.book_title, b.category, b.no_of_books, a.author_name, a.author_mail_id from book b left join author a on b.book_isbn = a.book_isbn";
		Book b = null;
		Author a = null;
		boolean flag = false;
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs != null)
			{
				flag = true;
				System.out.println("ISBN\tTitle\tCategory\tNo of books\tAuthor name\tAuthor mail id");
			}
			while(rs != null && rs.next())
			{
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return flag;
	}
	
	public boolean issueBook(BookIssue bi)
	{
		String sql = "insert into book_issue(issue_id, usn, issue_date, return_date, book_isbn) values(?, ?, ?, ?, ?)";
		int rows = 0;
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bi.getId());
			ps.setString(2, bi.getUsn());
			ps.setDate(3, (java.sql.Date)bi.getIssue_date());
			ps.setDate(4, (java.sql.Date)bi.getReturn_date());
			ps.setString(5, bi.getBook_isbn());
			
			rows = ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rows > 0;
	}
	
	public boolean listBookByUsn(String usn)
	{
		String sql = "select b.book_title, s.name, bi.return_date from book b, student s, book_issue bi where b.book_isbn = bi.book_isbn and bi.usn = s.usn and LOWER(bi.usn) = ?";
		boolean flag = false;
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usn.toLowerCase());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs != null)
			{
				flag = true;
				System.out.println("Book Title\tStudent Name\tReturn Date");
			}
			
			while(rs != null && rs.next())
			{
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + (java.util.Date)rs.getDate(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return flag;
	}
	
	public boolean getBookToReturn()
	{
		String sql = "select b.book_isbn, b.book_title, b.category from book b, book_issue bi where bi.book_isbn = b.book_isbn and return_date = ?";
		int rows = 0;
		

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now();  
	    Date d = null;
		try {
			d = new SimpleDateFormat("dd/MM/yyyy").parse(dtf.format(now));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}  
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, (java.sql.Date)d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows > 0;
	}
	
	
	
	

}
