package ru.otus.homework.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.After;
import ru.otus.homework.annotation.Before;
import ru.otus.homework.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TestRunner {

    private final Logger log = LoggerFactory.getLogger(TestRunner.class);

    public void run(Class<?> testClass) throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {
        List<Method> beforeMethods = getMethodsAnnotatedBy(testClass, Before.class);
        List<Method> testMethods = getMethodsAnnotatedBy(testClass, Test.class);
        List<Method> afterMethods = getMethodsAnnotatedBy(testClass, After.class);
        int allTests = testMethods.size();
        int passedTests = 0;

        for (Method testMethod : testMethods) {
            Object testObject = testClass.getDeclaredConstructor().newInstance();
            invokeMethods(beforeMethods, testObject);
            try {
                testMethod.invoke(testObject);
                passedTests++;
            } catch (InvocationTargetException ex) {
                log.error(ex.getTargetException().getMessage());
            }
            invokeMethods(afterMethods, testObject);
        }

        log.info("All tests: {}", allTests);
        log.info("Passed: {}", passedTests);
        log.info("Failed: {}", allTests - passedTests);
    }

    private static void invokeMethods(List<Method> beforeMethods, Object testObject) throws IllegalAccessException,
            InvocationTargetException {
        for (Method beforeMethod : beforeMethods) {
            beforeMethod.invoke(testObject);
        }
    }

    private List<Method> getMethodsAnnotatedBy(Class<?> testClass, Class<? extends Annotation> annotation) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .toList();
    }
}
