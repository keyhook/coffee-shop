package com.digital.infra.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Accessors(chain = true)
@Data
public class RegisterCustomerRequest {

  @NotBlank
  private String mobileNumber;

  @NotBlank
  @Length(max = 50)
  private String password;

  @NotBlank
  @Length(max = 100)
  private String name;

  private String homeAddress;

  private String workAddress;
}
