package com.aenustar.booktype;

import com.aenustar.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.json.JSONObject;

public class FictionBook extends Book {
    public static final String CATEGORY = "Fiction";

    private String genre;
    private boolean isSeries;
    private String seriesName;
    private int recommendedAge;

    // 构造函数
    public FictionBook(String title, String author, String publicationDate, String category) {
        super(title, author, publicationDate, category);
        // this.genre = genre;
        // this.isSeries = isSeries;
        // this.seriesName = seriesName;
        // this.recommendedAge = recommendedAge;
    }

    // Getter 和 Setter 方法
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isSeries() {
        return isSeries;
    }

    public void setSeries(boolean isSeries) {
        this.isSeries = isSeries;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public int getRecommendedAge() {
        return recommendedAge;
    }

    public void setRecommendedAge(int recommendedAge) {
        this.recommendedAge = recommendedAge;
    }

    // 返回所有参数的数组
    public String[] getAllParameters() {
        return new String[] {
            getTitle(),
            getAuthor(),
            //String.valueOf(getQuantity()),
            getPublicationDate(),
            getCategory(),
            genre,
            String.valueOf(isSeries),
            seriesName,
            String.valueOf(recommendedAge)
        };
    }

   @Override
    public void inputSpecialParameters(Scanner scanner) {
        System.out.println("请输入小说类型（直接回车跳过）：");
        this.genre = scanner.nextLine();
        if (genre.isEmpty()) {
            genre = "未指定";
        }
        System.out.println("是否为系列小说（yes/no，直接回车默认no）：");
        String isSeriesInput = scanner.nextLine();
        this.isSeries = isSeriesInput.equalsIgnoreCase("yes");
        System.out.println("请输入系列名称（直接回车跳过）：");
        this.seriesName = scanner.nextLine();
        if (seriesName.isEmpty()) {
            seriesName = "未指定";
        }
        System.out.println("请输入推荐年龄（直接回车默认0）：");
        String recommendedAgeInput = scanner.nextLine();
        if (!recommendedAgeInput.isEmpty()) {
            try {
                this.recommendedAge = Integer.parseInt(recommendedAgeInput);
            } catch (NumberFormatException e) {
                System.out.println("输入的推荐年龄无效，使用默认值0。");
                this.recommendedAge = 0;
            }
        } else {
            this.recommendedAge = 0;
        }
    }
    


    @Override
    public String getSpecialParameters() {
        JSONObject json = new JSONObject();
        json.put("genre", this.genre);
        json.put("isSeries", this.isSeries);
        json.put("seriesName", this.seriesName);
        json.put("recommendedAge", this.recommendedAge);
        return json.toString();
    }

    @Override
    public void setSpecialParameters(String specialParameterJson) {
        if (specialParameterJson != null && !specialParameterJson.isEmpty()) {
            JSONObject json = new JSONObject(specialParameterJson);
            this.genre = json.getString("genre");
            this.isSeries = json.getBoolean("isSeries");
            this.seriesName = json.getString("seriesName");
            this.recommendedAge = json.getInt("recommendedAge");
        }
    }


    
    // 静态方法获取书本数量
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




    // 显示书本信息
    @Override
    public void displayInfo() {
        System.out.println("Book ID: " + getId() +
                           ", Title: " + getTitle() +
                           ", Author: " + getAuthor() +
                           //", Quantity: " + getQuantity() +
                           ", Publication Date: " + getPublicationDate() +
                           ", Category: " + getCategory() +
                           ", Genre: " + genre +
                           ", Is Series: " + isSeries +
                           ", Series Name: " + seriesName +
                           ", Recommended Age: " + recommendedAge);
    }
}