package ru.otus.homework;

import ru.otus.homework.runner.TestRunner;
import ru.otus.homework.test.ExampleTest;

public class Main {

    public static void main(String[] args) throws Exception {
        TestRunner runner = new TestRunner();
        runner.run(ExampleTest.class);
    }
}
