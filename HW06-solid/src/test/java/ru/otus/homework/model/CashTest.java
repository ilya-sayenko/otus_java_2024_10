package ru.otus.homework.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.model.Banknote.FIFTY;
import static ru.otus.homework.model.Banknote.TEN;

public class CashTest {

    @Test
    public void shouldGetAllBanknotes() {
        Cash cash = new Cash();
        cash.add(TEN, 1);
        cash.add(FIFTY, 2);
        Set<Banknote> banknotes = new HashSet<>(cash.getAllBanknotes());

        assertTrue(banknotes.contains(TEN));
        assertTrue(banknotes.contains(FIFTY));
    }

    @Test
    public void shouldGetValueAfterAdd() {
        Cash cash = new Cash();
        int tenValue = 1;
        cash.add(TEN, tenValue);

        assertEquals(tenValue, cash.getValue(TEN));
        assertEquals(0, cash.getValue(FIFTY));
    }

    @Test
    public void shouldSubtract() {
        Cash cashOne = new Cash();
        cashOne.add(TEN, 1);
        cashOne.add(FIFTY, 1);
        Cash cashTwo = new Cash();
        cashTwo.add(TEN, 1);
        cashOne.subtract(cashTwo);

        assertEquals(0, cashOne.getValue(TEN));
        assertEquals(1, cashOne.getValue(FIFTY));
    }

    @Test
    public void shouldGetTotalValue() {
        Cash cashOne = new Cash();
        cashOne.add(TEN, 1);
        cashOne.add(FIFTY, 1);

        assertEquals(60, cashOne.getTotalValue());
    }
}