package com.digital.infra.rest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FieldErrorDto {

  private String name;

  private String desc;
}
