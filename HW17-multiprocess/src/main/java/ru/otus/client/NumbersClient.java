package ru.otus.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.NumberRequest;
import ru.otus.NumbersServiceGrpc;

public class NumbersClient {

    private static final Logger log = LoggerFactory.getLogger(NumbersClient.class);

    private static final int LOOP_LIMIT = 50;

    private long value = 0;

    public static void main(String[] args) {
        log.info("Numbers client is starting...");

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        var asyncClient = NumbersServiceGrpc.newStub(managedChannel);
        new NumbersClient().clientAction(asyncClient);

        log.info("Numbers client is shutting down...");
        managedChannel.shutdown();
    }

    private void clientAction(NumbersServiceGrpc.NumbersServiceStub asyncClient) {
        NumberRequest numberRequest = NumberRequest.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();
        ClientStreamObserver clientStreamObserver = new ClientStreamObserver();
        asyncClient.number(numberRequest, clientStreamObserver);

        for (int i = 0; i < LOOP_LIMIT; i++) {
            long val = getNextValue(clientStreamObserver);
            log.info("currentValue:{}", val);
            sleep();
        }
    }

    long getNextValue(ClientStreamObserver clientStreamObserver) {
        value = value + clientStreamObserver.getLastValueAndReset() + 1;

        return value;
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
