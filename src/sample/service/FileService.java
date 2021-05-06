package sample.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileService {

    private static FileService instance;

    public static FileService getInstance() {
        if (instance == null) {
            instance = new FileService();
        }
        return instance;
    }

    public StringBuilder toBinaryText(File file) throws IOException {
        StringBuilder binaryText = new StringBuilder();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            for (int b; (b = inputStream.read()) != -1; ) {
                String s = "0000000" + Integer.toBinaryString(b);
                s = s.substring(s.length() - 8);
                binaryText.append(s);
            }
        }
        return binaryText;
    }

    public void writeToFile(File file, String encryptText) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < encryptText.length(); i++) {
                temp.append(encryptText.charAt(i));
                if (temp.length() == 8) {
                    Scanner sc = new Scanner(temp.toString());
                    while (sc.hasNextInt()) {
                        try {
                            outputStream.write(sc.nextInt(2));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    temp = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return "*." + fileName.substring(fileName.lastIndexOf(".") + 1);

        } else {
            return "";
        }
    }


}
