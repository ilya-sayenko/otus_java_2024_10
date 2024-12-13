package ru.otus.homework.service;

import ru.otus.homework.model.Banknote;
import ru.otus.homework.model.Cash;

public interface CashMachine {

    Cash cashOut(int value);

    void cashIn(Banknote banknote, int value);

    int getBalance();
}
