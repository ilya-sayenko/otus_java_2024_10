package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorThrowEvenSecondTest {

    @Test
    public void shouldThrowWhenEvenSecond() {
        LocalDateTime localDateTime = LocalDateTime.of(1000, 1, 1, 1, 1, 2);
        Message message = new Message.Builder(1L).build();
        ProcessorThrowEvenSecond processor = new ProcessorThrowEvenSecond(localDateTime);

        assertThrows(RuntimeException.class, () -> processor.process(message));
    }

    @Test
    public void shouldProcessWhenOddSecond() {
        LocalDateTime localDateTime = LocalDateTime.of(1000, 1, 1, 1, 1, 1);
        Message message = new Message.Builder(1L).build();
        ProcessorThrowEvenSecond processor = new ProcessorThrowEvenSecond(localDateTime);

        assertDoesNotThrow(() -> processor.process(message));
    }
}