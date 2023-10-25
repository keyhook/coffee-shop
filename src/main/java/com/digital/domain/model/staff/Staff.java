package com.digital.domain.model.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "staff")
public class Staff {

  @Id
  @Setter(AccessLevel.NONE)
  private UUID id = UUID.randomUUID();

  private String email;

  @JsonIgnore
  private String password;

  private String name;

  @Enumerated(EnumType.STRING)
  private Type type;

  @Column(name = "shop_id")
  private UUID shopId;

  @CreationTimestamp
  @Setter(AccessLevel.NONE)
  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  @UpdateTimestamp
  @Setter(AccessLevel.NONE)
  @Column(name = "updated_at")
  private LocalDateTime updatedAt = LocalDateTime.now();

  @Version
  @Column(name = "version")
  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private short version = 0;

  public enum Type {
    OWNER,

    OPERATOR
  }
}
