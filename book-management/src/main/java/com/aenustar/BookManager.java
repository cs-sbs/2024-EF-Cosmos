package com.aenustar;

import com.aenustar.booktype.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookManager {
    private Connection connection;

    public BookManager(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Book book) {
        String query = "INSERT INTO books (title, author, publication_date, category, special_parameter) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublicationDate());
            stmt.setString(4, book.getCategory());
            stmt.setString(5, book.getSpecialParameters());
            stmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to add book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
            System.out.println("Book deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to delete book: " + e.getMessage());
        }
    }

    public void listBooks() {
        String query = "SELECT * FROM books";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Book book = createBookFromResultSet(rs);
                book.displayInfo();
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve books: " + e.getMessage());
        }
    }

    public void searchBooks(String keyword) {
        String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = createBookFromResultSet(rs);
                book.displayInfo();
            }
        } catch (SQLException e) {
            System.out.println("Failed to search books: " + e.getMessage());
        }
    }

    public void filterBooksByCategory(String category) {
        String query = "SELECT * FROM books WHERE category = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = createBookFromResultSet(rs);
                book.displayInfo();
            }
        } catch (SQLException e) {
            System.out.println("Failed to filter books by category: " + e.getMessage());
        }
    }

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
    String category = rs.getString("category");
    String title = rs.getString("title");
    String author = rs.getString("author");
    String publicationDate = rs.getString("publication_date");
    String specialParameter = rs.getString("special_parameter");

    Book book = BookFactory.createBook(category, new Scanner(System.in), title, author, publicationDate);
    book.setSpecialParameters(specialParameter);
    return book;
    }
}
