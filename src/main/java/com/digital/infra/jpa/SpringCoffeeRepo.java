package com.digital.infra.jpa;

import com.digital.domain.model.shop.Coffee;
import com.digital.domain.model.staff.Staff;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringCoffeeRepo extends JpaRepository<Coffee, UUID> {

  int countByIdIn(List<UUID> ids);

  List<Coffee> findByIdIn(List<UUID> ids);
}
