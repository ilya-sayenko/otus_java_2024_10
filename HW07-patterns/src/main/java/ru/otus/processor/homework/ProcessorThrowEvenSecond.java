package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorThrowEvenSecond implements Processor {

    private final LocalDateTime localDateTime;

    public ProcessorThrowEvenSecond(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public Message process(Message message) {
        if (localDateTime.getSecond() % 2 == 0) {
            throw new RuntimeException("Even second");
        }

        return message;
    }
}
