package com.digital.domain.model.shop;

import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class MenuItem {

  private UUID coffeeId;

  private Money price;

  public boolean valid() {
    return price.positive();
  }
}
