package com.aenustar.booktype;

import com.aenustar.Book;
import java.util.Scanner;

public class GeneralBook extends Book {
    public static final String CATEGORY = "General";

    public GeneralBook(String title, String author, String publicationDate, String category) {
        super(title, author, publicationDate, category);
    }

    @Override
    public void inputSpecialParameters(Scanner scanner) {
        // 一般图书没有特殊参数，方法体留空
    }

    @Override
    public String getSpecialParameters() {
        // 没有特殊参数，返回空字符串
        return "";
    }

    @Override
    public void setSpecialParameters(String specialParameterJson) {
        // 一般图书没有特殊参数，不需要处理
    }

    @Override
    public void displayInfo() {
        System.out.println("Book ID: " + getId() +
                           ", Title: " + getTitle() +
                           ", Author: " + getAuthor() +
                           ", Publication Date: " + getPublicationDate() +
                           ", Category: " + getCategory());
    }
}