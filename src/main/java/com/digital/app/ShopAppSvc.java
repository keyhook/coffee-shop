package com.digital.app;

import com.digital.domain.model.shop.Shop;
import java.util.Map;
import java.util.UUID;

public interface ShopAppSvc {

  Shop update(UpdateShopCmd cmd);

  Shop get(UUID id);

  Map<String, Object> getInfo(UUID id);
}
