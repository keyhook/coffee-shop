package com.digital.infra.jpa.converter;

import com.digital.domain.model.shop.Contact;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;
import lombok.SneakyThrows;

@Converter(autoApply = true)
public class ContactAttributeConverter implements
    AttributeConverter<Contact, String> {

  private final ObjectMapper objectMapper;

  public ContactAttributeConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  @SneakyThrows
  public String convertToDatabaseColumn(Contact attribute) {
    return Objects.isNull(attribute) ? null : objectMapper.writeValueAsString(attribute);
  }

  @Override
  @SneakyThrows
  public Contact convertToEntityAttribute(String dbData) {
    return Objects.isNull(dbData) ? null : objectMapper.readValue(dbData, new TypeReference<>() {

    });
  }
}
