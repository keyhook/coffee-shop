package com.digital.infra.rest;

import com.digital.domain.model.customer.CustomerDto;
import com.digital.domain.model.shop.Contact;
import com.digital.domain.model.shop.Currency;
import com.digital.domain.model.shop.Location;
import com.digital.domain.model.shop.MenuItem;
import com.digital.domain.model.shop.Money;
import com.digital.domain.model.shop.Order;
import com.digital.domain.model.shop.Order.Status;
import com.digital.domain.model.shop.OrderQueue;
import com.digital.domain.model.shop.Shop;
import com.digital.infra.jpa.SpringShopRepo;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Autowired
  private SpringShopRepo shopRepo;

  @GetMapping("/foo")
  public Object foo() {
    var shop = new Shop()
        .setLocation(new Location()
            .setLat(7.7)
            .setLng(9.9))
        .setContact(new Contact()
            .setCity("city")
            .setStreet("street")
            .setHouseNumber("house number").setPostalCode("postal code")
            .setPhoneNumber("111"))
        .setMenu(List.of(
            new MenuItem()
                .setCoffeeId(UUID.randomUUID())
                .setPrice(new Money(BigDecimal.ONE, Currency.SGD)),
            new MenuItem()
                .setCoffeeId(UUID.randomUUID())
                .setPrice(new Money(BigDecimal.TEN, Currency.SGD))))
        .setOrderQueues(List.of(
            new OrderQueue()
                .setId(1)
                .setOrders(List.of(new Order()
                    .setId(UUID.randomUUID())
                    .setCustomer(new CustomerDto()
                        .setId(UUID.randomUUID())
                        .setMobileNumber("777")
                        .setName("customer name")
                        .setScore(12))
                    .setMenuItem(new MenuItem()
                        .setCoffeeId(UUID.randomUUID())
                        .setPrice(new Money(BigDecimal.ONE, Currency.SGD)))
                    .setAmount(5)
                    .setStatus(Status.AWAITING_SERVE)))))
        .setAllowedOrderQueueCount(3)
        .setAllowedOrderQueueSize(100)
        .setOpeningTime(LocalTime.of(7, 0))
        .setClosingTime(LocalTime.of(19, 0));

    shopRepo.save(shop);

    return Map.of();
  }
}
