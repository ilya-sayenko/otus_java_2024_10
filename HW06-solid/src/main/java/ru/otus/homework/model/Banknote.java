package ru.otus.homework.model;

public enum Banknote {
    TEN(10),
    FIFTY(50),
    ONE_HUNDRED(100),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
