package com.digital.domain.model.shop;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Contact {

  private String street;

  private String houseNumber;

  private String postalCode;

  private String city;

  private String phoneNumber;
}
