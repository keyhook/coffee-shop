package com.digital.domain.repo;

import com.digital.domain.model.shop.Shop;
import com.digital.domain.model.staff.Staff;
import java.util.Optional;
import java.util.UUID;

public interface ShopRepo {

  Shop requireById(UUID id);

  void update(Shop shop);
}
