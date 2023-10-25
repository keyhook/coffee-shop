package com.digital.domain.model.shop;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Money {

  private final BigDecimal amount;

  private final Currency currency;

  public Money(BigDecimal amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public boolean positive() {
    return amount.compareTo(BigDecimal.ZERO) > 0;
  }
}
