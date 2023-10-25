package com.digital.app.helper;

import com.digital.domain.model.customer.CustomerToken;
import com.digital.domain.model.staff.StaffToken;

public interface CryptoUtil {

  boolean match(String plainText, String hashedText);

  String hash(String plainText);

  String signStaffToken(StaffToken staffToken);

  StaffToken verifyStaffToken(String tokenAsString);

  String signCustomerToken(CustomerToken customerToken);

  CustomerToken verifyCustomerToken(String tokenAsString);
}
