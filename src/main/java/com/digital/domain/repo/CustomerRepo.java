package com.digital.domain.repo;

import com.digital.domain.model.customer.Customer;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepo {

  Optional<Customer> findByMobileNumber(String mobileNumber);

  void requireUniqueMobileNumber(String mobileNumber);

  void create(Customer customer);

  void update(Customer customer);

  Customer requireById(UUID id);
}
