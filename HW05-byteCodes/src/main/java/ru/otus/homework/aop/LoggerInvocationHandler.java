package ru.otus.homework.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.Log;
import ru.otus.homework.service.CalculationService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggerInvocationHandler implements InvocationHandler {

    private final CalculationService calculationService;

    private final Set<String> logMethods;

    private static final Logger log = LoggerFactory.getLogger(LoggerInvocationHandler.class);

    public LoggerInvocationHandler(CalculationService calculationService) {
        this.calculationService = calculationService;
        logMethods = Arrays.stream(calculationService.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .map(this::convertToString)
                .collect(Collectors.toSet());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (logMethods.contains(convertToString(method))) {
            log.info("executed method: {}, parameters: {}", method.getName(), args);
        }

        return method.invoke(calculationService, args);
    }

    private String convertToString(Method method) {
        return method.getName() + Arrays.toString(method.getParameterTypes());
    }
}
