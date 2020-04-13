package pl.ryszardszwajlik.research.gcs.performance.data;

import java.nio.file.Files;
import java.nio.file.Paths;

public enum TestFile {
    FILE_1MB_ZIP("1mb.zip"),
    FILE_5MB_ZIP("5mb.zip"),
    FILE_10MB_ZIP("10mb.zip"),
    ;

    private final String fileName;
    private final byte[] content;

    TestFile(String fileName) {
        this.fileName = fileName;
        try {
            this.content = Files
                    .readAllBytes(Paths.get(this.getClass().getClassLoader().getResource(fileName).toURI()));
        } catch (Exception e) {
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
