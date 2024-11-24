package com.aenustar;

import java.util.Scanner;
import com.aenustar.booktype.*;

public class BookFactory {
    public static Book createBook(String category, Scanner scanner, String title, String author, String publicationDate) {
        Book book = null;
        switch (category) {
            case "Computer":
                book = new ComputerBook(title, author, publicationDate, category);
                break;
            case "Medical":
                book = new MedicalBook(title, author, publicationDate, category);
                break;
            case "Fiction":
                book = new FictionBook(title, author, publicationDate, category);
                break;
            default:
                book = new GeneralBook(title, author, publicationDate, category);
                break;
        }
        // 让书籍对象自行处理特殊参数的输入
        book.inputSpecialParameters(scanner);
        return book;
    }
}