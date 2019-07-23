Console Based Book Management System ( using JDBC)

The Book Management System (BMS) provides user the following functionalities
•	Add a Book
•	Search a Book based on Book Title
•	Search Books based on Category
•	Search Books based on Author
•	List All Books along with author information
•	Issue Book to Student
•	List Books issued to Student  based on USN number (Print Book title, Student Name and return date) 
•	List books which are to be returned for current date

Add Book : A user can add book by providing the following information, title, ISBN ( Book Number), category and Author information ( Author Name and Phone Number)

Search Book: Book search can be based on book title, or category or Author, when a book is found, entire information has to be printed on the screen. Partial searches to be supported for example, if user searches by ‘ja’,  ‘Java Complete Reference’, ‘Head First Java’ books should be displayed.

List Books : List All Books

Issue Book to Student:  While issuing a book to a student, first select a book and then issue the book. Take the student USN No and Student Name while issuing. Each book will be issued for a maximum of 7 days. System should calculate the return date and display to the user. If the book is not in library, you should inform the user of the unavailability of the book.

Implementation Details
Classes :  Book ,Author, Student, Issue
Book : Has an Author
Student : Has a List of books issued 
