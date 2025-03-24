package ru.otus.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.NumberRequest;
import ru.otus.NumberResponse;
import ru.otus.NumbersServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class NumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(NumbersServiceImpl.class);

    @Override
    public void number(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
        log.info("Request for the new sequence of numbers, firstValue:{}, lastValue:{}", request.getFirstValue(), request.getLastValue());
        AtomicLong currentValue = new AtomicLong(request.getFirstValue());
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            long value = currentValue.incrementAndGet();
            NumberResponse response = NumberResponse.newBuilder()
                    .setNumber(value)
                    .build();
            responseObserver.onNext(response);
            if (value == request.getLastValue()) {
                executor.shutdown();
                responseObserver.onCompleted();
                log.info("Sequence of numbers finished");
            }
        };
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
