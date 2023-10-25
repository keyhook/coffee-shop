package com.digital.domain.model.customer;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CustomerToken {

  public static final String MOBILE_NUMBER_KEY = "mobileNumber";

  public static final String NAME_KEY = "name";

  public static final String SCORE_KEY = "score";

  private UUID tokenId;

  private UUID customerId;

  private String mobileNumber;

  private String name;

  private Integer score;

  private LocalDateTime expiredAt;

  public void requireNotExpired() {
    if (expiredAt.isBefore(LocalDateTime.now())) {
      throw new DomainException(DomainCode.TOKEN_INVALID, Map.of(
          "customerId", customerId,
          "reason", "token expired"));
    }
  }
}
