package com.digital.infra.jpa;

import com.digital.domain.model.shop.Coffee;
import com.digital.domain.repo.CoffeeRepo;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class JpaCoffeeRepo implements CoffeeRepo {

  private final SpringCoffeeRepo springCoffeeRepo;

  public JpaCoffeeRepo(SpringCoffeeRepo springCoffeeRepo) {
    this.springCoffeeRepo = springCoffeeRepo;
  }

  @Override
  public int countByIds(List<UUID> ids) {
    if (ids.isEmpty()) {
      log.info("method: countByIds - idsSize: 0 - count: 0");

      return 0;
    }

    var count = springCoffeeRepo.countByIdIn(ids);

    log.info("method: countByIds - idsSize: {} - count: {}", ids.size(), count);

    return count;
  }

  @Override
  public List<Coffee> findByIds(List<UUID> ids) {
    if (ids.isEmpty()) {
      log.info("method: findByIds - idsSize: 0 - coffeesSize: 0");

      return List.of();
    }

    var coffees = springCoffeeRepo.findByIdIn(ids);

    log.info("method: countByIds - idsSize: {} - coffeesSize: {}", ids.size(), coffees.size());

    return coffees;
  }
}
