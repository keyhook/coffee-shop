package com.digital.domain.model.shop;

import static java.util.function.Predicate.not;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@ToString
@Entity
@Table(name = "shop")
public class Shop {

  private static int MAX_ORDER_QUEUE_COUNT = 3;

  private static int MAX_ORDER_QUEUE_SIZE = 300;

  @Id
  @Setter(AccessLevel.NONE)
  private UUID id = UUID.randomUUID();

  private Location location;

  private Contact contact;

  private List<MenuItem> menu = new ArrayList<>();

  private List<OrderQueue> orderQueues = new ArrayList<>();

  private int allowedOrderQueueCount;

  private int allowedOrderQueueSize;

  private LocalTime openingTime;

  private LocalTime closingTime;

  @CreationTimestamp
  @Setter(AccessLevel.NONE)
  private LocalDateTime createdAt = LocalDateTime.now();

  @UpdateTimestamp
  @Setter(AccessLevel.NONE)
  private LocalDateTime updatedAt = LocalDateTime.now();

  private UUID updatedBy;

  @Version
  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private short version = 0;

  public void adjustQueueCount(int allowedOrderQueueCount) {
    if (allowedOrderQueueCount > MAX_ORDER_QUEUE_COUNT) {
      throw new DomainException(DomainCode.QUEUE_COUNT_INVALID, Map.of(
          "id", id,
          "allowedOrderQueueCount", allowedOrderQueueCount,
          "reason", "allowed order queue count exceeds max value of " + MAX_ORDER_QUEUE_COUNT));
    }

    if (allowedOrderQueueCount < orderQueues.size()) {
      throw new DomainException(DomainCode.QUEUE_COUNT_INVALID, Map.of(
          "id", id,
          "allowedOrderQueueCount", allowedOrderQueueCount,
          "reason", "allowed order queue count is less than existing queue count of "
              + orderQueues.size()));
    }

    this.allowedOrderQueueCount = allowedOrderQueueCount;
  }

  private boolean hasQueueHavingSizeGreaterThan(int allowedOrderQueueSize) {
    return !orderQueues.isEmpty() &&
        orderQueues
            .stream()
            .anyMatch(orderQueue -> orderQueue.size() > allowedOrderQueueSize);
  }

  public void adjustQueueSize(int allowedOrderQueueSize) {
    if (allowedOrderQueueSize > MAX_ORDER_QUEUE_SIZE) {
      throw new DomainException(DomainCode.QUEUE_SIZE_INVALID, Map.of(
          "id", id,
          "allowedOrderQueueSize", allowedOrderQueueSize,
          "reason", "allowed order queue size exceeds max value of " + MAX_ORDER_QUEUE_SIZE));
    }

    if (hasQueueHavingSizeGreaterThan(allowedOrderQueueSize)) {
      throw new DomainException(DomainCode.QUEUE_SIZE_INVALID, Map.of(
          "id", id,
          "allowedOrderQueueSize", allowedOrderQueueSize,
          "reason", "allowed order queue size is less than existing queue size"));
    }

    this.allowedOrderQueueSize = allowedOrderQueueSize;
  }

  public void adjustMenu(List<MenuItem> menu) {
    menu
        .stream()
        .filter(not(MenuItem::valid))
        .findFirst()
        .ifPresentOrElse(
            menuItem -> {
              throw new DomainException(DomainCode.MENU_ITEM_INVALID, Map.of(
                  "id", id,
                  "menuItem", menuItem));
            },
            () -> this.menu = menu);
  }

  public void adjustWorkingTime(LocalTime openingTime, LocalTime closingTime) {
    if (openingTime.isAfter(closingTime)) {
      throw new DomainException(DomainCode.WORKING_TIME_INVALID, Map.of(
          "id", id,
          "openingTime", openingTime,
          "closingTime", closingTime));
    }

    this.openingTime = openingTime;
    this.closingTime = closingTime;
  }
}
