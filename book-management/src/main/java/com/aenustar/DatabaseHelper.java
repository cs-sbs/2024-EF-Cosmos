package com.aenustar;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;

public class DatabaseHelper {
    private List<Book> books;


    private static final String DATABASE_URL = "jdbc:sqlite:library.db";//book-management\src\main\resources
    private Connection connection;

    public DatabaseHelper() {

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            createTables();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }

    private void createTables() {
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                                  "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                  "title TEXT NOT NULL," +
                                  "author TEXT NOT NULL," +
                                  //"quantity INTEGER NOT NULL," +
                                  "publication_date TEXT NOT NULL," +
                                  "category TEXT NOT NULL," +
                                  "special_parameter TEXT NOT NULL);";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                                  "username TEXT PRIMARY KEY," +
                                  "password TEXT NOT NULL," +
                                  "role TEXT NOT NULL);";

        try (PreparedStatement stmt1 = connection.prepareStatement(createBooksTable);
             PreparedStatement stmt2 = connection.prepareStatement(createUsersTable)) {
            stmt1.execute();
            stmt2.execute();
        } catch (SQLException e) {
            System.out.println("Failed to create tables: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to close database connection: " + e.getMessage());
        }
    }
}
