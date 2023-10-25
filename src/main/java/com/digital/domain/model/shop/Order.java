package com.digital.domain.model.shop;

import com.digital.domain.model.customer.CustomerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Accessors(chain = true)
@Data
public class Order {

  @Id
  private UUID id;

  private CustomerDto customer;

  private MenuItem menuItem;

  private int amount;

  private Status status;

  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime updatedAt = LocalDateTime.now();

  public enum Status {

    AWAITING_SERVE,

    COMPLETED,

    CANCELLED
  }
}
