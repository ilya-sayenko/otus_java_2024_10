package ru.otus.dataprocessor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import ru.otus.model.Measurement;

public interface Loader {

    List<Measurement> load() throws IOException, URISyntaxException;
}