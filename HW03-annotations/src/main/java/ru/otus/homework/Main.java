package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.runner.TestResult;
import ru.otus.homework.runner.TestRunner;
import ru.otus.homework.test.ExampleTest;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        TestRunner runner = new TestRunner();
        TestResult testResult = runner.run(ExampleTest.class);

        log.info("All tests: {}", testResult.allTests());
        log.info("Passed: {}", testResult.passedTests());
        log.info("Failed: {}", testResult.failedTests());
        log.info("Results:\n{}", testResult.results());
    }
}
