package com.cruds.bms;

import java.util.Date;

public class BookIssue {
	
	private int id;
	public String usn;
	private Date issue_date;
	private Date return_date;
	public String book_isbn;
	
	public BookIssue(int id, String usn, Date issue_date, Date return_date, String book_isbn) {
		this.id = id;
		this.usn = usn;
		this.issue_date = issue_date;
		this.return_date = return_date;
		this.book_isbn = book_isbn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsn() {
		return usn;
	}

	public void setUsn(String usn) {
		this.usn = usn;
	}

	public Date getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

	public String getBook_isbn() {
		return book_isbn;
	}

	public void setBook_isbn(String book_isbn) {
		this.book_isbn = book_isbn;
	}

	@Override
	public String toString() {
		return "BookIssue [id=" + id + ", usn=" + usn + ", issue_date=" + issue_date + ", return_date=" + return_date
				+ ", book_isbn=" + book_isbn + "]";
	}
		

}
