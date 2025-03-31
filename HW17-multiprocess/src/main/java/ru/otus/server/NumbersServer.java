package ru.otus.server;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NumbersServer {

    private static final Logger log = LoggerFactory.getLogger(NumbersServer.class);

    private static final int PORT = 8080;

    public static void main(String[] args) throws InterruptedException, IOException {
        log.info("Numbers Server is starting...");

        Server server = ServerBuilder.forPort(PORT)
                .addService(new NumbersServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown request");
            server.shutdown();
            log.info("Server stopped");
        }));

        log.info("Server is waiting for client, port:{}", PORT);
        server.awaitTermination();
    }
}
