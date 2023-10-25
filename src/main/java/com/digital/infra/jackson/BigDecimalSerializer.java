package com.digital.infra.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalSerializer extends StdSerializer<BigDecimal> {

  public BigDecimalSerializer() {
    this(BigDecimal.class);
  }

  public BigDecimalSerializer(Class<BigDecimal> t) {
    super(t);
  }

  @Override
  public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.stripTrailingZeros().toPlainString());
  }
}
