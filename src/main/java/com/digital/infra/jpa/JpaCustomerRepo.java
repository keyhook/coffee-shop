package com.digital.infra.jpa;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.customer.Customer;
import com.digital.domain.repo.CustomerRepo;
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
public class JpaCustomerRepo implements CustomerRepo {

  private final SpringCustomerRepo springCustomerRepo;

  private final EntityManager entityManager;

  public JpaCustomerRepo(
      SpringCustomerRepo springCustomerRepo,
      EntityManager entityManager) {
    this.springCustomerRepo = springCustomerRepo;
    this.entityManager = entityManager;
  }

  @Override
  public Optional<Customer> findByMobileNumber(String mobileNumber) {
    var customer = springCustomerRepo.findByMobileNumber(mobileNumber);

    log.info("method: findByMobileNumber - mobileNumber: {} - customer: {}", mobileNumber, customer);

    return customer;
  }

  @Override
  public void requireUniqueMobileNumber(String mobileNumber) {
    if (springCustomerRepo.existsByMobileNumber(mobileNumber)) {
      throw new DomainException(DomainCode.MOBILE_NUMBER_DUPLICATED, Map.of("mobileNumber", mobileNumber));
    }

    log.info("method: requireUniqueMobileNumber - mobileNumber: {}", mobileNumber);
  }

  @Override
  @Transactional
  public void create(Customer customer) {
    entityManager.persist(customer);

    log.info("method: create - customer: {}", customer);
  }

  @Override
  @Transactional
  public void update(Customer customer) {
    entityManager
        .unwrap(Session.class)
        .update(customer);

    log.info("method: update - customer: {}", customer);
  }

  @Override
  public Customer requireById(UUID id) {
    var customer = springCustomerRepo
        .findById(id)
        .orElseThrow(
            () -> new DomainException(DomainCode.ENTITY_NOT_FOUND, Map.of(
                "entityType", Customer.class.getSimpleName(),
                "entityId", id)));

    log.info("method: requireById - id: {} - customer: {}", id, customer);

    return customer;
  }
}
