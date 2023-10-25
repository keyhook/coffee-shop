package com.digital.app;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class LoginStaffCmd {

  private String email;

  private String password;
}
