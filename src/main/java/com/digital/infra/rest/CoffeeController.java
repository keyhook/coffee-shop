package com.digital.infra.rest;

import com.digital.app.CoffeeAppSvc;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {

  private final CoffeeAppSvc coffeeAppSvc;

  public CoffeeController(CoffeeAppSvc coffeeAppSvc) {
    this.coffeeAppSvc = coffeeAppSvc;
  }

  @GetMapping("/v1/coffees")
  public Object get(@RequestParam List<UUID> ids) {
    return Map.of("records", coffeeAppSvc.get(ids));
  }
}
