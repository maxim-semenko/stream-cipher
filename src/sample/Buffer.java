package sample;

public class Buffer {

    public static Buffer instance;
    private String fileExtension;

    public static Buffer getInstance() {
        if (instance == null) {
            instance = new Buffer();
        }
        return instance;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
