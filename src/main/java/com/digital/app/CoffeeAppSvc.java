package com.digital.app;

import com.digital.domain.model.shop.Coffee;
import java.util.List;
import java.util.UUID;

public interface CoffeeAppSvc {

  List<Coffee> get(List<UUID> ids);
}
