package com.fishb0ness.autoawaremonitor.domain.measures;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Money {
    private BigDecimal amount;
    private Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        validateAmount(amount);
        validateCurrency(currency);
        this.amount = amount;
        this.currency = currency;
    }

    // getters and setters
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        validateCurrency(currency);
        this.currency = currency;
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be null or less than 0");
        }
    }

    private void validateCurrency(Currency currency) {
        Objects.requireNonNull(currency, "Currency cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 && currency.equals(money.currency);
    }
}
