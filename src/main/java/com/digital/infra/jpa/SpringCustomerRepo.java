package com.digital.infra.jpa;

import com.digital.domain.model.customer.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringCustomerRepo extends JpaRepository<Customer, UUID> {

  Optional<Customer> findByMobileNumber(String mobileNumber);

  boolean existsByMobileNumber(String mobileNumber);
}
