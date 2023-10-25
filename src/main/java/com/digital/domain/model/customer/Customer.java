package com.digital.domain.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "password")
@Entity
@Table(name = "customer")
public class Customer {

  @Id
  @Setter(AccessLevel.NONE)
  private UUID id = UUID.randomUUID();

  private String mobileNumber;

  @JsonIgnore
  private String password;

  private String name;

  private String homeAddress;

  private String workAddress;

  private Integer score;

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
