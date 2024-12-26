package ru.otus.dataprocessor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface Serializer {

    void serialize(Map<String, Double> data) throws IOException, URISyntaxException;
}
