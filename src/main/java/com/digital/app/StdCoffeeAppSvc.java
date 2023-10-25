package com.digital.app;

import com.digital.domain.model.shop.Coffee;
import com.digital.domain.repo.CoffeeRepo;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StdCoffeeAppSvc implements CoffeeAppSvc {

  private final CoffeeRepo coffeeRepo;

  public StdCoffeeAppSvc(CoffeeRepo coffeeRepo) {
    this.coffeeRepo = coffeeRepo;
  }

  @Override
  public List<Coffee> get(List<UUID> ids) {
    log.info("method: get - idsSize: {}", ids.size());

    var coffees = coffeeRepo.findByIds(ids);

    log.info("method: get - coffeesSize: {}", coffees.size());

    return coffees;
  }
}
