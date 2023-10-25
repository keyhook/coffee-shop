package com.digital.infra.jpa;

import com.digital.domain.model.shop.Shop;
import com.digital.domain.model.staff.Staff;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringShopRepo extends JpaRepository<Shop, UUID> {

}
