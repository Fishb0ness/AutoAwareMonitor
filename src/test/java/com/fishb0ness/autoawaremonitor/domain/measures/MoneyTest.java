package com.fishb0ness.autoawaremonitor.domain.measures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyTest {

    @Test
    public void moneyInDollarsIsStoredCorrectly() {
        Money money = new Money(new BigDecimal("10.99"), Currency.getInstance("USD"));
        Assertions.assertEquals(new BigDecimal("10.99"), money.getAmount());
    }

    @Test
    public void moneyInEurosIsStoredCorrectly() {
        Money money = new Money(new BigDecimal("9.99"), Currency.getInstance("EUR"));
        Assertions.assertEquals(new BigDecimal("9.99"), money.getAmount());
    }

    @Test
    public void currencyIsStoredCorrectly() {
        Money money = new Money(new BigDecimal("10.99"), Currency.getInstance("USD"));
        Assertions.assertEquals(Currency.getInstance("USD"), money.getCurrency());
    }

    @Test
    public void moneyCanBeUpdated() {
        Money money = new Money(new BigDecimal("10.99"), Currency.getInstance("USD"));
        money.setAmount(new BigDecimal("20.99"));
        Assertions.assertEquals(new BigDecimal("20.99"), money.getAmount());
    }

    @Test
    public void currencyCanBeUpdated() {
        Money money = new Money(new BigDecimal("10.99"), Currency.getInstance("USD"));
        money.setCurrency(Currency.getInstance("EUR"));
        Assertions.assertEquals(Currency.getInstance("EUR"), money.getCurrency());
    }

    @Test
    public void moneyCannotBeNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Money(new BigDecimal("-1.0"), Currency.getInstance("USD")));
    }

    @Test
    public void currencyCannotBeNull() {
        Assertions.assertThrows(NullPointerException.class, () -> new Money(new BigDecimal("10.0"), null));
    }
}
