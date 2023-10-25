package com.digital.domain.repo;

import com.digital.domain.model.shop.Coffee;
import java.util.List;
import java.util.UUID;

public interface CoffeeRepo {

  int countByIds(List<UUID> ids);

  List<Coffee> findByIds(List<UUID> ids);
}
