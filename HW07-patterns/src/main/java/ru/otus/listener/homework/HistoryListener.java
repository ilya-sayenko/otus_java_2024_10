package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        history.put(msg.getId(), copy(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }

    private Message copy(Message msg) {
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(new ArrayList<>(msg.getField13().getData()));

        return msg.toBuilder().field13(objectForMessage).build();
    }
}
