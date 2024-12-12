package ru.otus.homework.service;

import ru.otus.homework.exception.IncorrectCashValueException;
import ru.otus.homework.model.Banknote;
import ru.otus.homework.model.Cash;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class CashMachineImpl implements CashMachine {

    private final Cash cash = new Cash();

    @Override
    public Cash cashOut(int value) {
        Cash cashForOut = new Cash();
        AtomicInteger remainder = new AtomicInteger(value);
        Cash balanceCash = new Cash(cash);
        balanceCash.getBanknotesWithPositiveValues()
                .stream()
                .sorted(Comparator.comparingInt(Banknote::getValue).reversed())
                .forEach(banknote -> {
                    int banknoteValue = banknote.getValue();
                    while (remainder.get() - banknoteValue >= 0 && balanceCash.getValue(banknote) > 0) {
                        remainder.addAndGet(-banknoteValue);
                        balanceCash.add(banknote, -1);
                        cashForOut.add(banknote, 1);
                    }
                });

        if (remainder.get() > 0) {
            throw new IncorrectCashValueException(String.format("It is impossible to cash out the value %s", value));
        }

        cash.subtract(cashForOut);

        return cashForOut;
    }

    @Override
    public void cashIn(Banknote banknote, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value should be more then zero");
        }
        cash.add(banknote, value);
    }

    @Override
    public int getBalance() {
        return cash.getTotalValue();
    }
}
