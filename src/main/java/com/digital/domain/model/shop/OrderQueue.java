package com.digital.domain.model.shop;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class OrderQueue {

  private int id;

  private List<Order> orders;

  public int size() {
    return orders.size();
  }
}
