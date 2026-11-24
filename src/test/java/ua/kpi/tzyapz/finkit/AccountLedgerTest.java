package ua.kpi.tzyapz.finkit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.kpi.tzyapz.finkit.LedgerEntry.Type.*;

@DisplayName("AccountLedger: BL-70, BL-80")
class AccountLedgerTest {

    private AccountLedger ledger;

    @BeforeEach
    void setUp() {
        ledger = new AccountLedger();
        ledger.deposit(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("BL-70: deposit 500 на новий рахунок — баланс 500.00, один запис DEPOSIT")
    void depositIncreasesBalanceAndAddsEntry() {
        AccountLedger fresh = new AccountLedger();

        fresh.deposit(new BigDecimal("500"));

        LedgerEntry e = fresh.history().get(0);
        assertAll(
                () -> assertEquals(new BigDecimal("500.00"), fresh.balance()),
                () -> assertEquals(1, fresh.history().size()),
                () -> assertEquals(DEPOSIT, e.type()),
                () -> assertEquals(new BigDecimal("500.00"), e.balanceAfter()));
    }

    @ParameterizedTest(name = "BL-70: сума {0} відхиляється, баланс не змінюється")
    @NullSource
    @ValueSource(strings = {"0", "-0.01"})
    void nonPositiveAmountsThrowAndKeepBalance(String amount) {
        BigDecimal a = amount == null ? null : new BigDecimal(amount);
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> ledger.deposit(a)),
                () -> assertThrows(IllegalArgumentException.class, () -> ledger.withdraw(a)),
                () -> assertEquals(new BigDecimal("100.00"), ledger.balance()));
    }

    // BUG-20: не можна списати весь баланс
    @ParameterizedTest(name = "BL-70: баланс 100.00, списання {0} → {1}")
    @CsvSource({"99.99,0.01", "100.00,0.00"})
    void withdrawUpToBalanceIsAllowed(String amount, String left) {
        assertDoesNotThrow(() -> ledger.withdraw(new BigDecimal(amount)));
        assertEquals(new BigDecimal(left), ledger.balance());
    }

    // BUG-22: кидає IllegalStateException замість IllegalArgumentException (SRS-1.1)
    @Test
    @DisplayName("BL-70: списання 100.01 при балансі 100.00 — IllegalArgumentException, баланс не змінився")
    void overdraftIsRejectedAndBalanceUnchanged() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> ledger.withdraw(new BigDecimal("100.01"))),
                () -> assertEquals(new BigDecimal("100.00"), ledger.balance()));
    }

    // BUG-21: історія у зворотному порядку
    @Test
    @DisplayName("BL-70: історія хронологічна і незмінна")
    void historyIsInChronologicalOrder() {
        ledger.withdraw(new BigDecimal("20.50"));
        List<LedgerEntry> h = ledger.history();
        assertAll(
                () -> assertIterableEquals(List.of(DEPOSIT, WITHDRAWAL), h.stream().map(LedgerEntry::type).toList()),
                () -> assertThrows(UnsupportedOperationException.class, () -> h.add(h.get(0))));
    }

    @ParameterizedTest(name = "BL-80: deposit {0} → +{1}")
    @CsvSource({"10.125,10.13", "0.005,0.01", "2.344,2.34"})
    void amountsAreRoundedHalfUpToCents(String amount, String added) {
        ledger.deposit(new BigDecimal(amount));
        assertEquals(new BigDecimal("100.00").add(new BigDecimal(added)), ledger.balance());
    }
}
