package com.aenustar;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupThread extends Thread {
    private String sourceFilePath;    // 源数据库文件路径
    private String targetDirectory;   // 备份目标目录
    private long interval;            // 备份间隔时间（毫秒）

    public BackupThread(String sourceFilePath, String targetDirectory, long interval) {
        this.sourceFilePath = sourceFilePath;
        this.targetDirectory = targetDirectory;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 等待指定的时间间隔
                Thread.sleep(interval);

                // 执行备份
                backupDatabase();

            } catch (InterruptedException e) {
                System.out.println("备份线程被中断：" + e.getMessage());
                break;
            } catch (IOException e) {
                System.out.println("备份数据库文件失败：" + e.getMessage());
            }
        }
    }

    private void backupDatabase() throws IOException {
        // 创建备份文件名，包含时间戳
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String backupFileName = "library_backup_" + timestamp + ".db";

        Path sourcePath = Paths.get(sourceFilePath);
        Path targetPath = Paths.get(targetDirectory, backupFileName);

        // 执行文件复制
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("数据库文件已备份至：" + targetPath.toString());
    }
}