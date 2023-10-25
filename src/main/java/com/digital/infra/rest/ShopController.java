package com.digital.infra.rest;

import static com.digital.infra.rest.StaffTokenFilter.STAFF_TOKEN_ATTRIBUTE;

import com.digital.app.ShopAppSvc;
import com.digital.app.UpdateShopCmd;
import com.digital.domain.model.staff.StaffToken;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {

  private final ShopAppSvc shopAppSvc;

  public ShopController(ShopAppSvc shopAppSvc) {
    this.shopAppSvc = shopAppSvc;
  }

  @PutMapping("/internal/v1/shops/{shopId}")
  public Object update(
      @RequestAttribute(STAFF_TOKEN_ATTRIBUTE) StaffToken staffToken,
      @PathVariable UUID shopId,
      @Valid @RequestBody UpdateShopRequest request) {
    staffToken.requireOwnerType();
    staffToken.requireWorkFor(shopId);

    var cmd = new UpdateShopCmd()
        .setStaffId(staffToken.getStaffId())
        .setShopId(shopId)
        .setLocation(request.getLocation())
        .setContact(request.getContact())
        .setMenu(request.getMenu())
        .setAllowedOrderQueueCount(request.getAllowedOrderQueueCount())
        .setAllowedOrderQueueSize(request.getAllowedOrderQueueSize())
        .setOpeningTime(request.getOpeningTime())
        .setClosingTime(request.getClosingTime());

    return shopAppSvc.update(cmd);
  }

  @GetMapping("/internal/v1/shops/{shopId}")
  public Object get(
      @RequestAttribute(STAFF_TOKEN_ATTRIBUTE) StaffToken staffToken,
      @PathVariable UUID shopId) {
    staffToken.requireWorkFor(shopId);

    return shopAppSvc.get(shopId);
  }

  @GetMapping("/v1/shops/{shopId}")
  public Object get(@PathVariable UUID shopId) {
    return shopAppSvc.getInfo(shopId);
  }
}
