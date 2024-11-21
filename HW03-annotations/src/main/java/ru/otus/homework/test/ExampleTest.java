package ru.otus.homework.test;

import ru.otus.homework.annotation.After;
import ru.otus.homework.annotation.Before;
import ru.otus.homework.annotation.Test;

public class ExampleTest {

    @Before
    public void beforeOne() {
        System.out.println("before-one");
    }

    @Before
    public void beforeTwo() {
        System.out.println("before-two");
    }

    @Test
    public void testOne() {
        System.out.println("test-one");
        throw new RuntimeException("example");
    }

    @Test
    public void testTwo() {
        System.out.println("test-two");
    }

    @Test
    public void testThree() {
        System.out.println("test-three");
    }

    @After
    public void afterOne() {
        System.out.println("after-one");
    }

    @After
    public void afterTwo() {
        System.out.println("after-two");
    }
}
