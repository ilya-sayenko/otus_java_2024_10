package ru.petrelevich.service;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.petrelevich.domain.Message;
import ru.petrelevich.repository.MessageRepository;

@Service
public class DataStoreR2dbc implements DataStore {
    private static final String SPECIAL_ROOM = "1408";
    private static final Logger log = LoggerFactory.getLogger(DataStoreR2dbc.class);
    private final MessageRepository messageRepository;
    private final Scheduler workerPool;

    public DataStoreR2dbc(Scheduler workerPool, MessageRepository messageRepository) {
        this.workerPool = workerPool;
        this.messageRepository = messageRepository;
    }

    @Override
    public Mono<Message> saveMessage(Message message) {
        log.info("saveMessage:{}", message);
        if (message.roomId().equals(SPECIAL_ROOM)) {
            throw new RuntimeException("It is impossible to save message into room " + SPECIAL_ROOM);
        }
        return messageRepository.save(message);
    }

    @Override
    public Flux<Message> loadMessages(String roomId) {
        log.info("loadMessages roomId:{}", roomId);
        if (roomId.equals(SPECIAL_ROOM)) {
            return messageRepository.findAll().delayElements(Duration.of(3, SECONDS), workerPool);
        } else {
            return messageRepository.findByRoomId(roomId).delayElements(Duration.of(3, SECONDS), workerPool);
        }
    }
}
