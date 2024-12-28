package ru.otus.dataprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        try(InputStream inputStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            return Arrays.asList(objectMapper.readValue(inputStream, Measurement[].class));
        }
    }
}
