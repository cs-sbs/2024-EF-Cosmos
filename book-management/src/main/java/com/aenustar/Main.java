package com.aenustar;
import com.aenustar.booktype.*;

import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        DatabaseHelper dbHelper = new DatabaseHelper();


                // 获取数据库文件路径
        String databaseFilePath = "library.db";
        // 指定备份目录
        String backupDirectory = "backup";
        // 创建备份目录（如果不存在）
        File backupDir = new File(backupDirectory);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }
        // 设置备份间隔（例如，每隔1小时备份一次）
        long backupInterval = 3600000L; // 1小时 = 3600000毫秒

        // 启动备份线程
        BackupThread backupThread = new BackupThread(databaseFilePath, backupDirectory, backupInterval);
        backupThread.setDaemon(true); // 设置为守护线程
        backupThread.start();


        UserManager userManager = new UserManager(dbHelper.getConnection());
        BookManager bookManager = new BookManager(dbHelper.getConnection());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("欢迎使用图书管理系统");
            System.out.println("1. 注册");
            System.out.println("2. 登录");
            System.out.println("3. 退出");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除换行符
            if (choice == 1) {
                System.out.println("请输入用户名：");
                String username = scanner.nextLine();
                System.out.println("请输入密码：");
                String password = scanner.nextLine();
                System.out.println("请选择角色（user/admin）：");
                String role = scanner.nextLine();
                if (userManager.registerUser(username, password, role)) {
                    System.out.println("注册成功！");
                } else {
                    System.out.println("注册失败！");
                }

            }else if (choice == 3) {
                System.out.println("退出成功！");
                dbHelper.close();
                break;
            } 
            
            else if (choice == 2) {

                System.out.println("请输入用户名：");
                String username = scanner.nextLine();
                System.out.println("请输入密码：");
                String password = scanner.nextLine();
                String role = userManager.loginUser(username, password);
                if (role != null) {
                    System.out.println("登录成功！角色：" + role);
                    while (true) {
                        if (role.equals("admin")) {
                            System.out.println("1. 添加图书");
                            System.out.println("2. 删除图书");
                            System.out.println("3. 退出");

                            int adminChoice = scanner.nextInt();
                            scanner.nextLine(); // 清除换行符
                            if (adminChoice == 1) {
                                System.out.println("请输入书名：");
                                String title = scanner.nextLine();
                                System.out.println("请输入作者：");
                                String author = scanner.nextLine();
                                scanner.nextLine(); // 清除换行符
                                System.out.println("请输入出版日期：");
                                String publicationDate = scanner.nextLine();
                                System.out.println("请输入类别：(for example: Computer, Fiction, Medical, General)");
                                
                                String category = scanner.nextLine();
                                Book book = BookFactory.createBook(category, scanner, title, author, publicationDate);
                                    bookManager.addBook(book);

                            } else if (adminChoice == 2) {//删除图书
                                System.out.println("请输入书籍ID：");
                                int bookId = scanner.nextInt();
                                bookManager.deleteBook(bookId);


                            }else if (adminChoice == 3) {
                                System.out.println("退出成功！");
                                break;
                                //dbHelper.saveBooksToFile();
                            }
                        } else if (role.equals("user")) {
                            System.out.println("1. 查看所有图书");
                            System.out.println("2. 搜索图书");
                            System.out.println("3. 按类别筛选图书");
                            System.out.println("4.退出");
    
                            int userChoice = scanner.nextInt();
                            scanner.nextLine(); // 清除换行符
                            if (userChoice == 1) {
                                bookManager.listBooks();
                            } else if (userChoice == 2) {
                                System.out.println("请输入关键字：");
                                String keyword = scanner.nextLine();
                                bookManager.searchBooks(keyword);
                            } else if (userChoice == 3) {
                                System.out.println("请输入类别：");
                                String category = scanner.nextLine();
                                bookManager.filterBooksByCategory(category);
                            }else if (userChoice == 4) {
                                System.out.println("退出成功！");
                                break;
                            }
                        }
                    
                    }

                } else {
                    System.out.println("登录失败！");
                }
            }
        }
    }
}