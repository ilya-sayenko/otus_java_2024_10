package ru.otus.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Printer {
    private static final Logger logger = LoggerFactory.getLogger(Printer.class);
    private int number = 1;
    private int delta = 1;
    private String currentThread = "t1";

    public static void main(String[] args) {
        Printer printer = new Printer();
        new Thread(() -> printer.action("t1")).start();
        new Thread(() -> printer.action("t2")).start();
    }

    private synchronized void action(String threadName) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (threadName.equals(currentThread)) {
                    this.wait();
                }
                logger.info(String.valueOf(number));
                currentThread = threadName;

                if (threadName.equals("t1")) {
                    if (number == 10) {
                        delta = -1;
                    } else if (number == 1) {
                        delta = 1;
                    }
                    number += delta;
                }

                sleep();
                this.notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
