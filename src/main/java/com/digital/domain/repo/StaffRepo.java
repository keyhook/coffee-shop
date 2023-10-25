package com.digital.domain.repo;

import com.digital.domain.model.staff.Staff;
import java.util.Optional;
import java.util.UUID;

public interface StaffRepo {

  Optional<Staff> findByEmail(String email);

  Staff requireById(UUID id);
}
