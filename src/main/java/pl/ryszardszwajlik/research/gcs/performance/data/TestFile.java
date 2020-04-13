package pl.ryszardszwajlik.research.gcs.performance.data;

import java.io.IOException;

public enum TestFile {
    FILE_1MB_TEXT("1mb.txt"),
    FILE_5MB_TEXT("5mb.txt"),
    FILE_10MB_TEXT("10mb.txt"),
    FILE_1MB_ZIP("1mb.zip"),
    FILE_5MB_ZIP("5mb.zip"),
    FILE_10MB_ZIP("10mb.zip"),
    ;

    private final String fileName;
    private final byte[] content;

    TestFile(String fileName) {
        this.fileName = fileName;
        try {
            this.content = this.getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file", e);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }
}
