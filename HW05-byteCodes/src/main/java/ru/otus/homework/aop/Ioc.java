package ru.otus.homework.aop;

import ru.otus.homework.service.CalculationService;
import ru.otus.homework.service.CalculationServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc {

    public static CalculationService createCalculationService() {
        InvocationHandler handler = new LoggerInvocationHandler(new CalculationServiceImpl());

        return (CalculationService) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(),
                new Class<?>[]{CalculationService.class},
                handler
        );
    }
}
