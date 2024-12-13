package ru.otus.homework.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cash {

    private final Map<Banknote, Integer> cashBox = new HashMap<>();

    public Cash() {
    }

    public Cash(Cash cash) {
        for (Banknote banknote : cash.getAllBanknotes()) {
            cashBox.put(banknote, cash.getValue(banknote));
        }
    }

    public List<Banknote> getAllBanknotes() {
        return cashBox.keySet().stream()
                .toList();
    }

    public List<Banknote> getBanknotesWithPositiveValues() {
        return cashBox.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();
    }

    public int getValue(Banknote banknote) {
        return cashBox.getOrDefault(banknote, 0);
    }

    public void add(Banknote banknote, int value) {
        cashBox.merge(banknote, value, Integer::sum);
    }

    public void subtract(Cash cash) {
        for (Banknote banknote : cash.getAllBanknotes()) {
            cashBox.merge(banknote, -cash.getValue(banknote), Integer::sum);
        }
    }

    public int getTotalValue() {
        return cashBox.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }
}
