package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.service.AppService;
import sample.service.FileService;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private Button btnClearAll;

    @FXML
    private Button btnGenerateKey;

    @FXML
    private TextField stateRegisters;

    @FXML
    private MenuItem menuOpenFile;

    @FXML
    private MenuItem menuSaveFile;

    @FXML
    private Button btnEncrypt;

    @FXML
    private Button btnDecrypt;

    @FXML
    private TextArea inputSourceText;

    @FXML
    private TextArea inputKey;

    @FXML
    private TextArea inputEncryptText;
    @FXML
    private TextArea inputDecryptText;

    private final FileService fileService = FileService.getInstance();
    private final AppService appService = AppService.getInstance();

    @FXML
    void initialize() {

        inputSourceText.setWrapText(true);
        inputKey.setWrapText(true);
        inputEncryptText.setWrapText(true);
        inputDecryptText.setWrapText(true);

        // Открыть файл
        menuOpenFile.setOnAction(ActionEvent -> openFile());

        // Сохранить файл
        menuSaveFile.setOnAction(ActionEvent -> fileService.writeToFile(appService.saveFile(), inputDecryptText.getText()));

        // Генерация ключа
        btnGenerateKey.setOnAction(ActionEvent -> generateKey());

        // Шифрование
        btnEncrypt.setOnAction(ActionEvent -> encrypt());

        // Дешифрование
        btnDecrypt.setOnAction(ActionEvent -> decrypt());

        // Отчистить все
        btnClearAll.setOnAction(ActionEvent -> clearAll());
    }

    // Открыть файл
    private void openFile() {
        try {
            File file = appService.openFile();
            Buffer.getInstance().setFileExtension(fileService.getFileExtension(file));
            inputSourceText.setText(fileService.toBinaryText(file).toString());
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }

    // Генерация ключа
    private void generateKey() {
        if (inputSourceText.getText().isEmpty()) {
            appService.showErrorMessage("Откройте файл!");
        } else {
            StringBuilder clearRegisters = new StringBuilder();
            for (int i = 0; i < stateRegisters.getText().length(); i++) {
                if (stateRegisters.getText().charAt(i) == '1' || stateRegisters.getText().charAt(i) == '0') {
                    clearRegisters.append(stateRegisters.getText().charAt(i));
                }
            }
            if (clearRegisters.length() == 32) {
                try {
                    inputKey.setText(new LFSR().getKey(inputSourceText.getText().length(), clearRegisters.toString()).toString());
                } catch (NumberFormatException e) {
                    appService.showErrorMessage("Введите корректное состояние регистров!");
                }
            } else {
                appService.showErrorMessage("Введите длину, равную 32!");
            }
        }
    }

    // Шифровать
    private void encrypt() {
        if (inputKey.getText().isEmpty() || inputSourceText.getText().isEmpty()) {
            AppService.getInstance().showErrorMessage("Не хватает введенных данных!");
        } else {
            inputEncryptText.setText(appService.doProcess(inputSourceText.getText(), inputKey.getText()));
            File file = new File("Зашифрованный файл" + Buffer.getInstance().getFileExtension().replace("*", ""));
            fileService.writeToFile(file, inputEncryptText.getText());
        }
    }

    // Дешифрование
    private void decrypt() {
        if (inputKey.getText().isEmpty() || inputEncryptText.getText().isEmpty()) {
            appService.showErrorMessage("Не хватает введенных данных!");
        } else {
            inputDecryptText.setText(appService.doProcess(inputEncryptText.getText(), inputKey.getText()));
        }
    }

    private void clearAll() {
        inputSourceText.clear();
        inputKey.clear();
        stateRegisters.clear();
        inputEncryptText.clear();
        inputDecryptText.clear();
    }

}