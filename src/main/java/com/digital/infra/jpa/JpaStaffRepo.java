package com.digital.infra.jpa;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.staff.Staff;
import com.digital.domain.repo.StaffRepo;
import jakarta.persistence.EntityManager;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class JpaStaffRepo implements StaffRepo {

  private final SpringStaffRepo springStaffRepo;

  public JpaStaffRepo(SpringStaffRepo springStaffRepo) {
    this.springStaffRepo = springStaffRepo;
  }

  @Override
  public Staff requireById(UUID id) {
    var staff = springStaffRepo
        .findById(id)
        .orElseThrow(
            () -> new DomainException(DomainCode.ENTITY_NOT_FOUND, Map.of(
                "entityType", Staff.class.getSimpleName(),
                "entityId", id)));

    log.info("method: requireById - id: {} - staff: {}", id, staff);

    return staff;
  }

  @Override
  public Optional<Staff> findByEmail(String email) {
    var staff = springStaffRepo.findByEmail(email);

    log.info("method: findByEmail - email: {} - staff: {}", email, staff);

    return staff;
  }
}
