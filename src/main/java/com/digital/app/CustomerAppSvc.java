package com.digital.app;

import com.digital.domain.model.customer.Customer;

public interface CustomerAppSvc {

  Customer register(RegisterCustomerCmd cmd);

  String login(LoginCustomerCmd cmd);
}
