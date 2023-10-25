package com.digital.infra.jpa;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.shop.Shop;
import com.digital.domain.model.staff.Staff;
import com.digital.domain.repo.ShopRepo;
import com.digital.domain.repo.StaffRepo;
import jakarta.persistence.EntityManager;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class JpaShopRepo implements ShopRepo {

  private final EntityManager entityManager;

  private final SpringShopRepo springShopRepo;

  public JpaShopRepo(
      SpringShopRepo springShopRepo,
      EntityManager entityManager) {
    this.springShopRepo = springShopRepo;
    this.entityManager = entityManager;
  }

  @Override
  public Shop requireById(UUID id) {
    var shop = springShopRepo
        .findById(id)
        .orElseThrow(
            () -> new DomainException(DomainCode.ENTITY_NOT_FOUND, Map.of(
                "entityType", Shop.class.getSimpleName(),
                "entityId", id)));

    log.info("method: requireById - id: {} - shop: {}", id, shop);

    return shop;
  }

  @Override
  public void update(Shop shop) {
    entityManager
        .unwrap(Session.class)
        .update(shop);

    log.info("method: update - shop: {}", shop);
  }
}
