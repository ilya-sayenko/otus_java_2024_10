package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorSwapTest {

    @Test
    public void shouldSwapFields() {
        Message message = new Message.Builder(1L)
                .field11("11")
                .field12("12")
                .build();
        ProcessorSwap processorSwap = new ProcessorSwap();
        Message processedMessage = processorSwap.process(message);

        assertEquals(message.getField12(), processedMessage.getField11());
        assertEquals(message.getField11(), processedMessage.getField12());
    }
}