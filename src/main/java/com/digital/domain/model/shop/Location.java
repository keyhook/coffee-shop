package com.digital.domain.model.shop;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Location {

  private double lat;

  private double lng;
}
