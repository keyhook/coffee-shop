package com.digital.infra.rest;

import com.digital.app.LoginStaffCmd;
import com.digital.app.StaffAppSvc;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaffController {

  private final StaffAppSvc staffAppSvc;

  public StaffController(StaffAppSvc staffAppSvc) {
    this.staffAppSvc = staffAppSvc;
  }

  @PostMapping("/v1/staffs/login")
  public Object login(@Valid @RequestBody LoginStaffRequest request) {
    var cmd = new LoginStaffCmd()
        .setEmail(request.getEmail())
        .setPassword(request.getPassword());

    return Map.of("token", staffAppSvc.login(cmd));
  }
}
