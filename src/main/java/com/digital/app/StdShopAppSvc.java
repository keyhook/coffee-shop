package com.digital.app;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.shop.Shop;
import com.digital.domain.repo.CoffeeRepo;
import com.digital.domain.repo.ShopRepo;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StdShopAppSvc implements ShopAppSvc {

  private final ShopRepo shopRepo;

  private final CoffeeRepo coffeeRepo;

  public StdShopAppSvc(
      ShopRepo shopRepo,
      CoffeeRepo coffeeRepo) {
    this.shopRepo = shopRepo;
    this.coffeeRepo = coffeeRepo;
  }

  private void requireCoffeesValid(UpdateShopCmd cmd) {
    var coffeeCount = coffeeRepo.countByIds(cmd.getCoffeeIds());

    if (coffeeCount != cmd.getMenuSize()) {
      throw new DomainException(DomainCode.COFFEES_INVALID, Map.of(
          "menuSize", cmd.getMenuSize(),
          "foundCoffeeSize", coffeeCount));
    }
  }

  @Override
  public Shop update(UpdateShopCmd cmd) {
    log.info("method: update - cmd: {}", cmd);

    requireCoffeesValid(cmd);

    var shop = shopRepo.requireById(cmd.getShopId());

    shop
        .setLocation(cmd.getLocation())
        .setContact(cmd.getContact());

    shop.adjustMenu(cmd.getMenu());
    shop.adjustQueueCount(cmd.getAllowedOrderQueueCount());
    shop.adjustQueueSize(cmd.getAllowedOrderQueueSize());
    shop.adjustWorkingTime(cmd.getOpeningTime(), cmd.getClosingTime());

    shop.setUpdatedBy(cmd.getStaffId());

    shopRepo.update(shop);

    log.info("method: update - shop: {}", shop);

    return shop;
  }

  @Override
  public Shop get(UUID id) {
    log.info("method: get - id: {}", id);

    var shop = shopRepo.requireById(id);

    log.info("method: get - shop: {}", shop);

    return shop;
  }

  @Override
  public Map<String, Object> getInfo(UUID id) {
    var shop = get(id);

    return Map.of(
        "id", shop.getId(),
        "location", shop.getLocation(),
        "contact", shop.getContact(),
        "menu", shop.getMenu(),
        "openingTime", shop.getOpeningTime(),
        "closingTime", shop.getClosingTime(),
        "updatedAt", shop.getUpdatedAt());
  }
}
