package com.aenustar;

import java.sql.*;
import java.util.Scanner;


public abstract class Book{

    //private static final long serialVersionUID = 1L;
    public abstract void displayInfo();
        // 处理特殊参数的输入
    public abstract void inputSpecialParameters(Scanner scanner);
    
    // 获取特殊参数，返回JSON字符串
    public abstract String getSpecialParameters();
    public void setSpecialParameters(String specialParameterJson) {
        // 默认实现，子类根据需要重写
    }



    private int id;
    private String title;
    private String author;
    //private int quantity;//这个是不是可以删掉
    private String publicationDate;
    private String category;

    public Book(String title, String author, String publicationDate, String category) {
        this.title = title;
        this.author = author;
        //this.quantity = quantity;
        this.publicationDate = publicationDate;
        this.category = category;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // public int getQuantity() {
    //     return quantity;
    // }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getCategory() {
        return category;
    }

    public  String[] getAllParameters() {
        return new String[] {
            getTitle(),
            getAuthor(),
            //String.valueOf(getQuantity()),
            getPublicationDate(),
            getCategory()
        };
    }



    public static int getBookCount(Connection connection) {
        String query = "SELECT COUNT(*) AS count FROM books";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("Failed to get book count: " + e.getMessage());
        }
        return 0;
    }
}



    // @Override
    // public String toString() {
    //     return "Book ID: " + id + ", Title: " + title + ", Author: " + author + 
    //            ", Quantity: " + quantity + ", Publication Date: " + publicationDate + 
    //            ", Category: " + category;
    // }
