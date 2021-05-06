package sample.service;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import sample.Buffer;

import java.io.File;

public class AppService {

    private static AppService instance;

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    // Всплывающие окно, говорящее об ошибке
    public void showErrorMessage(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error message!");
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    public File saveFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Любой файл", Buffer.getInstance().getFileExtension());
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(null);
    }

    public File openFile() {
        FileChooser fileChooser = new FileChooser();
        try {
            return fileChooser.showOpenDialog(null);
        } catch (NullPointerException e) {
            System.out.println("error: " + e);
        }
        return null;
    }

    // Тут делается Xor
    public String doProcess(String text, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(text.charAt(i) ^ key.charAt(i));
        }
        return result.toString();
    }
}