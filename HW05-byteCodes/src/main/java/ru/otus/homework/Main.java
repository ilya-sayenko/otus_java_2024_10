package ru.otus.homework;

import ru.otus.homework.aop.Ioc;
import ru.otus.homework.service.CalculationService;

public class Main {

    public static void main(String[] args) {
        CalculationService calculationService = Ioc.createCalculationService();

        calculationService.calculation(1, 2);
        calculationService.calculation(1);
        calculationService.calculation(1, 2, 3);
    }
}
