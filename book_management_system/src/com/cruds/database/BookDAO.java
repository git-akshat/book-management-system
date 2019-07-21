package com.cruds.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cruds.entity.Book;

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
	
	public Book getByTitle(String title)
	{
		String sql = "select book_isbn, book_title, category, no_of_books from book where title = ?";
		Book b = null;
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return b;
	}
	
	public Book getByCategory(String category)
	{
		String sql = "select book_isbn, book_title, category, no_of_books from book where category = ?";
		Book b = null;
		
		try(Connection conn = DBConnectionManager.getConnection())
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, category);
			
			ResultSet rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return b;
	}

}
