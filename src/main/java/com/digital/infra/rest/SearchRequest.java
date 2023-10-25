package com.digital.infra.rest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SearchRequest {

  @Size(max = 10)
  private List<String> filters = List.of();

  @Size(max = 5)
  private List<String> sorts = List.of();

  @Min(0)
  @Max(50)
  private int page = 0;

  @Min(5)
  @Max(100)
  private int pageSize = 10;
}
