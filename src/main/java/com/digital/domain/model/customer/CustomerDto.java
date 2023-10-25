package com.digital.domain.model.customer;

import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CustomerDto {

  private UUID id;

  private String mobileNumber;

  private String name;

  private Integer score;
}
