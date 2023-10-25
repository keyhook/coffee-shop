package com.digital.infra.jpa.converter;

import com.digital.domain.model.shop.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;
import lombok.SneakyThrows;

@Converter(autoApply = true)
public class LocationAttributeConverter implements
    AttributeConverter<Location, String> {

  private final ObjectMapper objectMapper;

  public LocationAttributeConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  @SneakyThrows
  public String convertToDatabaseColumn(Location attribute) {
    return Objects.isNull(attribute) ? null : objectMapper.writeValueAsString(attribute);
  }

  @Override
  @SneakyThrows
  public Location convertToEntityAttribute(String dbData) {
    return Objects.isNull(dbData) ? null : objectMapper.readValue(dbData, new TypeReference<>() {

    });
  }
}
