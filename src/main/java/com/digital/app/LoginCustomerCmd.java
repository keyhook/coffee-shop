package com.digital.app;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@ToString(exclude = "password")
public class LoginCustomerCmd {

  private String mobileNumber;

  private String password;
}
