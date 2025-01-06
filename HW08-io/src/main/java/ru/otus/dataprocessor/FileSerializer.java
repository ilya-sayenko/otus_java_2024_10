package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        Files.writeString(Path.of(fileName), objectMapper.writeValueAsString(data));
    }
}
