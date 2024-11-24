package com.aenustar;

import java.util.Random;
import java.util.Scanner;

import com.aenustar.*;
import com.aenustar.booktype.*;


//此文加仅在程序初始化的时候执行一次, 用于向数据库中插入随机数据


public class RandomBookInserter {
    public static void main(String[] args) {

        // 初始化数据库助手和书籍管理器
        DatabaseHelper dbHelper = new DatabaseHelper();
        BookManager bookManager = new BookManager(dbHelper.getConnection());

        // 定义一些随机数据源
        String[] titles = {"Java编程思想", "医学概论", "奇幻之旅", "通用技能手册", "算法导论", "人体解剖学", "史诗传奇", "心理学基础", "设计模式", "急救指南"};
        String[] authors = {"张伟", "李娜", "王强", "刘洋", "陈杰", "杨磊", "赵敏", "孙丽", "周婷", "吴刚"};
        String[] computerLanguages = {"Java", "C++", "Python", "JavaScript", "Go"};
        String[] medicalFields = {"内科", "外科", "儿科", "皮肤科", "神经科"};
        String[] genres = {"奇幻", "科幻", "悬疑", "浪漫", "历史"};
        String[] categories = {"Computer", "Medical", "Fiction", "General"};

        Random random = new Random();

        int numberOfBooks = 20; // 要添加的书籍数量

        for (int i = 0; i < numberOfBooks; i++) {
            // 随机选择书籍基本信息
            String title = titles[random.nextInt(titles.length)] + " 第" + (i + 1) + "版";
            String author = authors[random.nextInt(authors.length)];
            String publicationDate = "202" + random.nextInt(4) + "-0" + (random.nextInt(9) + 1) + "-" + (random.nextInt(28) + 1);
            String category = categories[random.nextInt(categories.length)];

            // 根据类别创建书籍
            Book book = null;
            switch (category) {
                case "Computer":
                    ComputerBook computerBook = new ComputerBook(title, author, publicationDate, category);
                    computerBook.setProgrammingLanguage(computerLanguages[random.nextInt(computerLanguages.length)]);
                    book = computerBook;
                    break;
                case "Medical":
                    MedicalBook medicalBook = new MedicalBook(title, author, publicationDate, category);
                    medicalBook.setMedicalField(medicalFields[random.nextInt(medicalFields.length)]);
                    book = medicalBook;
                    break;
                case "Fiction":
                    FictionBook fictionBook = new FictionBook(title, author, publicationDate, category);
                    fictionBook.setGenre(genres[random.nextInt(genres.length)]);
                    fictionBook.setSeries(random.nextBoolean());
                    fictionBook.setSeriesName("系列" + (random.nextInt(5) + 1));
                    fictionBook.setRecommendedAge(12 + random.nextInt(10));
                    book = fictionBook;
                    break;
                default:
                    book = new GeneralBook(title, author, publicationDate, category);
                    break;
            }

            // 将书籍添加到数据库
            bookManager.addBook(book);
        }

        // 关闭数据库连接
        dbHelper.close();

        System.out.println("随机书籍添加完成！");
    }
}