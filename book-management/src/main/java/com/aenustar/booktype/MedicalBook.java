package com.aenustar.booktype;

import com.aenustar.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONObject;


public class MedicalBook extends Book {
    public static final String CATEGORY = "Medical";
    
//special parameter
    private String medicalField;

    public MedicalBook(String title, String author, String publicationDate, String category) {
        super(title, author ,publicationDate, category);
    }

    public String getMedicalField() {
        return medicalField;
    }

    public void setMedicalField(String medicalField) {
        this.medicalField = medicalField;
    }

    @Override
    public void inputSpecialParameters(Scanner scanner) {
        System.out.print("Medical Field: ");
        medicalField = scanner.nextLine();
    }

    @Override
    public String getSpecialParameters() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("medicalField", medicalField);
        return jsonObject.toString();
    }

    @Override
    public void setSpecialParameters(String specialParameterJson) {
        JSONObject jsonObject = new JSONObject(specialParameterJson);
        medicalField = jsonObject.getString("medicalField");
    }
    
    public static int getBookCount (Connection connection) {
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM book WHERE category = ?");
            statement.setString(1, MedicalBook.CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void displayInfo() {
        System.out.println("Book ID: " + getId() +
                           ", Title: " + getTitle() +
                           ", Author: " + getAuthor() +
                           //", Quantity: " + getQuantity() +
                           ", Publication Date: " + getPublicationDate() +
                           ", Category: " + getCategory() +
                           ", Medical Field: " + medicalField);
    }
}