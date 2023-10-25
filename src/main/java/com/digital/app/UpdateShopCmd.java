package com.digital.app;

import com.digital.domain.model.shop.Contact;
import com.digital.domain.model.shop.Location;
import com.digital.domain.model.shop.MenuItem;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateShopCmd {

  private UUID staffId;

  private UUID shopId;

  private Location location;

  private Contact contact;

  private List<MenuItem> menu;

  private int allowedOrderQueueCount;

  private int allowedOrderQueueSize;

  private LocalTime openingTime;

  private LocalTime closingTime;

  public List<UUID> getCoffeeIds() {
    return menu
        .stream()
        .map(MenuItem::getCoffeeId)
        .toList();
  }

  public int getMenuSize() {
    return menu.size();
  }
}
