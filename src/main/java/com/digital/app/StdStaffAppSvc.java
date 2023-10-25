package com.digital.app;

import com.digital.app.helper.CryptoUtil;
import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.staff.Staff;
import com.digital.domain.model.staff.StaffToken;
import com.digital.domain.repo.StaffRepo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StdStaffAppSvc implements StaffAppSvc {

  private static final Duration TOKEN_MAX_AGE = Duration.ofDays(1);

  private final StaffRepo staffRepo;

  private final CryptoUtil cryptoUtil;

  public StdStaffAppSvc(
      StaffRepo staffRepo,
      CryptoUtil cryptoUtil) {
    this.staffRepo = staffRepo;
    this.cryptoUtil = cryptoUtil;
  }

  private Staff requireStaffExisting(String email) {
    return staffRepo
        .findByEmail(email)
        .orElseThrow(() -> new DomainException(DomainCode.CREDENTIALS_INVALID,
            Map.of("email", email)));
  }

  private void requirePasswordMatching(Staff staff, String password) {
    if (!cryptoUtil.match(password, staff.getPassword())) {
      throw new DomainException(DomainCode.CREDENTIALS_INVALID,
          Map.of("email", staff.getEmail()));
    }
  }

  private String createToken(Staff staff) {
    return cryptoUtil.signStaffToken(new StaffToken()
        .setTokenId(UUID.randomUUID())
        .setStaffId(staff.getId())
        .setStaffType(staff.getType())
        .setShopId(staff.getShopId())
        .setExpiredAt(LocalDateTime.now().plus(TOKEN_MAX_AGE))
    );
  }

  @Override
  public String login(LoginStaffCmd cmd) {
    log.info("method: login - cmd: {}", cmd);

    var staff = requireStaffExisting(cmd.getEmail());

    requirePasswordMatching(staff, cmd.getPassword());

    var token = createToken(staff);

    log.info("method: login");

    return token;
  }
}
