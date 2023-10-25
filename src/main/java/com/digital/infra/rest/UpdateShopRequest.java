package com.digital.infra.rest;

import com.digital.domain.model.shop.Contact;
import com.digital.domain.model.shop.Location;
import com.digital.domain.model.shop.MenuItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateShopRequest {

  @NotNull
  private Location location;

  @NotNull
  private Contact contact;

  @NotEmpty
  private List<MenuItem> menu;

  @NotNull
  private Integer allowedOrderQueueCount;

  @NotNull
  private Integer allowedOrderQueueSize;

  @NotNull
  private LocalTime openingTime;

  @NotNull
  private LocalTime closingTime;
}
