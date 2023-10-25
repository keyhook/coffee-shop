package com.digital.domain.model.staff;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.staff.Staff.Type;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class StaffToken {

  public static final String STAFF_TYPE_KEY = "staffType";

  public static final String SHOP_ID_KEY = "shopId";

  private UUID tokenId;

  private UUID staffId;

  private Type staffType;

  private UUID shopId;

  private LocalDateTime expiredAt;

  public void requireNotExpired() {
    if (expiredAt.isBefore(LocalDateTime.now())) {
      throw new DomainException(DomainCode.TOKEN_INVALID, Map.of(
          "staffId", staffId,
          "reason", "token expired"));
    }
  }

  public void requireOwnerType() {
    if (staffType != Type.OWNER) {
      throw new DomainException(DomainCode.UNAUTHORIZED, Map.of(
          "staffId", staffId,
          "reason", "invalid staff"));
    }
  }

  public void requireWorkFor(UUID shopId) {
    if (!Objects.equals(this.shopId, shopId)) {
      throw new DomainException(DomainCode.UNAUTHORIZED, Map.of(
          "staffId", staffId,
          "reason", "invalid shop"));
    }
  }
}
