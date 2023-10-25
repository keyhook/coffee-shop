package com.digital.infra.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LoginStaffRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;
}
