package ru.otus.homework.runner;

import java.util.Map;

public record TestResult(
        int allTests,
        int passedTests,
        int failedTests,
        Map<String, Boolean> results
) {
}
