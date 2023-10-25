package com.digital.infra.rest;

import com.digital.app.RegisterCustomerCmd;
import com.digital.app.CustomerAppSvc;
import com.digital.app.LoginCustomerCmd;
import com.digital.domain.model.shop.Contact;
import com.digital.domain.model.shop.Currency;
import com.digital.domain.model.shop.Location;
import com.digital.domain.model.shop.MenuItem;
import com.digital.domain.model.shop.Money;
import com.digital.domain.model.shop.Shop;
import com.digital.domain.repo.ShopRepo;
import com.digital.infra.jpa.SpringShopRepo;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  private final CustomerAppSvc customerAppSvc;

  public CustomerController(CustomerAppSvc customerAppSvc) {
    this.customerAppSvc = customerAppSvc;
  }

  @PostMapping("/v1/customers")
  public Object register(@Valid @RequestBody RegisterCustomerRequest request) {
    var cmd = new RegisterCustomerCmd()
        .setMobileNumber(request.getMobileNumber())
        .setPassword(request.getPassword())
        .setName(request.getName())
        .setHomeAddress(request.getHomeAddress())
        .setWorkAddress(request.getWorkAddress());

    return customerAppSvc.register(cmd);
  }

  @PostMapping("/v1/customers/login")
  public Object login(@Valid @RequestBody LoginCustomerRequest request) {
    var cmd = new LoginCustomerCmd()
        .setMobileNumber(request.getMobileNumber())
        .setPassword(request.getPassword());

    return Map.of("token", customerAppSvc.login(cmd));
  }
}
