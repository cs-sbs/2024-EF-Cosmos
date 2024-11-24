package com.aenustar.booktype;

import com.aenustar.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONObject;

public class ComputerBook extends Book {
    public static final String CATEGORY = "Computer";

//special parameter
    private String programmingLanguage;

    public ComputerBook(String title, String author, String publicationDate, String category) {
        super(title, author, publicationDate, category);
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }



    @Override
    public void inputSpecialParameters(Scanner scanner) {
        System.out.print("输入编程语言: ");
        programmingLanguage = scanner.nextLine();//回车是null?
    }


    @Override
    public String getSpecialParameters() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("programmingLanguage", programmingLanguage);
        return jsonObject.toString();
    }
    @Override
    public void setSpecialParameters(String specialParameterJson) {
        JSONObject jsonObject = new JSONObject(specialParameterJson);
        programmingLanguage = jsonObject.getString("programmingLanguage");
    }
    
    
    public static int getBookCount (Connection connection) {
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM book WHERE category = ?");
            statement.setString(1, ComputerBook.CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    @Override
    public void displayInfo() {
        System.out.println("Book ID: " + getId() +
                           ", Title: " + getTitle() +
                           ", Author: " + getAuthor() +
                           //", Quantity: " + getQuantity() +
                           ", Publication Date: " + getPublicationDate() +
                           ", Category: " + getCategory() +
                           ", Programming Language: " + programmingLanguage);
    }
}