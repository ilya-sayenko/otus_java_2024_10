package ru.otus.dataprocessor;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data.stream()
                .sorted(Comparator.comparing(Measurement::name))
                .collect(Collectors.toMap(
                        Measurement::name,
                        Measurement::value,
                        Double::sum,
                        LinkedHashMap::new));
    }
}
