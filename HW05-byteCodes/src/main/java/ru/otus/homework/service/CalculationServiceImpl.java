package ru.otus.homework.service;

import ru.otus.homework.annotation.Log;

public class CalculationServiceImpl implements CalculationService {

    @Log
    @Override
    public int calculation(int param1) {
        return param1;
    }

    @Log
    @Override
    public int calculation(int param1, int param2) {
        return param1 + param2;
    }

    @Override
    public int calculation(int param1, int param2, int param3) {
        return param1 + param2 + param3;
    }
}
