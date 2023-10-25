package com.digital.domain.model.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "coffee")
public class Coffee {

  @Id
  @Setter(AccessLevel.NONE)
  private UUID id = UUID.randomUUID();

  private String name;

  @CreationTimestamp
  @Setter(AccessLevel.NONE)
  private LocalDateTime createdAt = LocalDateTime.now();

  @UpdateTimestamp
  @Setter(AccessLevel.NONE)
  private LocalDateTime updatedAt = LocalDateTime.now();

  @Version
  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private short version = 0;
}
