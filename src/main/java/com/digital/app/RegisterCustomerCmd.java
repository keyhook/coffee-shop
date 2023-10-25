package com.digital.app;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(exclude = "password")
public class RegisterCustomerCmd {

  private String mobileNumber;

  private String password;

  private String name;

  private String homeAddress;

  private String workAddress;
}
