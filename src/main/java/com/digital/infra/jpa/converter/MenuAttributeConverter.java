package com.digital.infra.jpa.converter;

import com.digital.domain.model.shop.MenuItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;

@Converter(autoApply = true)
public class MenuAttributeConverter implements
    AttributeConverter<List<MenuItem>, String> {

  private final ObjectMapper objectMapper;

  public MenuAttributeConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  @SneakyThrows
  public String convertToDatabaseColumn(List<MenuItem> attribute) {
    return Objects.isNull(attribute) ? null : objectMapper.writeValueAsString(attribute);
  }

  @Override
  @SneakyThrows
  public List<MenuItem> convertToEntityAttribute(String dbData) {
    return Objects.isNull(dbData) ? null : objectMapper.readValue(dbData, new TypeReference<>() {

    });
  }
}
