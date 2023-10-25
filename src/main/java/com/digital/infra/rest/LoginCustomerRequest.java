package com.digital.infra.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LoginCustomerRequest {

  @NotBlank
  private String mobileNumber;

  @NotBlank
  private String password;
}
