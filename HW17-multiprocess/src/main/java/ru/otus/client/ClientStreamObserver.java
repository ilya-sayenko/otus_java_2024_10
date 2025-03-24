package ru.otus.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.NumberResponse;

public class ClientStreamObserver implements StreamObserver<NumberResponse> {

    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);

    private long lastValue = 0;

    @Override
    public void onNext(NumberResponse numberResponse) {
        log.info("new value:{}", numberResponse.getNumber());
        setLastValue(numberResponse.getNumber());
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("Error:", throwable);
    }

    @Override
    public void onCompleted() {
        log.info("Request completed");
    }

    public synchronized long getLastValueAndReset() {
        long prev = this.lastValue;
        this.lastValue = 0;

        return prev;
    }

    private synchronized void setLastValue(long value) {
        this.lastValue = value;
    }
}
