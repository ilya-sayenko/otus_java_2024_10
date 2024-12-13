package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import ru.otus.homework.exception.IncorrectCashValueException;
import ru.otus.homework.model.Cash;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.model.Banknote.FIFTY;
import static ru.otus.homework.model.Banknote.ONE_HUNDRED;
import static ru.otus.homework.model.Banknote.TEN;

public class CashMachineImplTest {

    @Test
    public void shouldCashOut() {
        CashMachineImpl cashMachineOne = new CashMachineImpl();
        cashMachineOne.cashIn(ONE_HUNDRED, 2);
        cashMachineOne.cashIn(FIFTY, 2);
        cashMachineOne.cashIn(TEN, 10);
        Cash resultCashOne = cashMachineOne.cashOut(350);

        assertEquals(2, resultCashOne.getValue(FIFTY));
        assertEquals(2, resultCashOne.getValue(ONE_HUNDRED));
        assertEquals(5, resultCashOne.getValue(TEN));
        assertEquals(50, cashMachineOne.getBalance());

        CashMachineImpl cashMachineTwo = new CashMachineImpl();
        cashMachineTwo.cashIn(ONE_HUNDRED, 1);
        cashMachineTwo.cashIn(FIFTY, 2);
        cashMachineTwo.cashIn(TEN, 1);
        Cash resultCashTwo = cashMachineTwo.cashOut(110);

        assertEquals(0, resultCashTwo.getValue(FIFTY));
        assertEquals(1, resultCashTwo.getValue(ONE_HUNDRED));
        assertEquals(1, resultCashTwo.getValue(TEN));
        assertEquals(100, cashMachineTwo.getBalance());
    }

    @Test
    public void shouldNotCashOutForIncorrectValue() {
        CashMachineImpl cashMachine = new CashMachineImpl();
        cashMachine.cashIn(ONE_HUNDRED, 5);
        cashMachine.cashIn(FIFTY, 1);
        cashMachine.cashIn(TEN, 10);

        assertThrows(IncorrectCashValueException.class, () -> cashMachine.cashOut(351));
    }

    @Test
    public void shouldNotCashOutForIfNotEnoughCash() {
        CashMachineImpl cashMachine = new CashMachineImpl();
        cashMachine.cashIn(ONE_HUNDRED, 5);
        cashMachine.cashIn(FIFTY, 1);
        cashMachine.cashIn(TEN, 10);

        assertThrows(IncorrectCashValueException.class, () -> cashMachine.cashOut(10000));
    }

    @Test
    public void shouldCashIn() {
        CashMachineImpl cashMachine = new CashMachineImpl();

        assertDoesNotThrow(() -> cashMachine.cashIn(TEN, 1));
    }

    @Test
    public void shouldGetBalance() {
        CashMachineImpl cashMachine = new CashMachineImpl();
        cashMachine.cashIn(ONE_HUNDRED, 5);
        cashMachine.cashIn(FIFTY, 1);
        cashMachine.cashIn(TEN, 10);

        assertEquals(650, cashMachine.getBalance());
    }
}
