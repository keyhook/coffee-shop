package com.digital.app;

import com.digital.app.helper.CryptoUtil;
import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.customer.Customer;
import com.digital.domain.model.customer.CustomerToken;
import com.digital.domain.repo.CustomerRepo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StdCustomerAppSvc implements CustomerAppSvc {

  private static final Duration TOKEN_MAX_AGE = Duration.ofDays(7);

  private final CustomerRepo customerRepo;

  private final CryptoUtil cryptoUtil;

  public StdCustomerAppSvc(
      CustomerRepo customerRepo,
      CryptoUtil cryptoUtil) {
    this.customerRepo = customerRepo;
    this.cryptoUtil = cryptoUtil;
  }

  @Override
  public Customer register(RegisterCustomerCmd cmd) {
    log.info("method: create - cmd: {}", cmd);

    customerRepo.requireUniqueMobileNumber(cmd.getMobileNumber());

    var hashedPassword = cryptoUtil.hash(cmd.getPassword());

    var customer = new Customer()
        .setMobileNumber(cmd.getMobileNumber())
        .setPassword(hashedPassword)
        .setName(cmd.getName())
        .setHomeAddress(cmd.getHomeAddress())
        .setWorkAddress(cmd.getWorkAddress())
        .setScore(0);

    customerRepo.create(customer);

    log.info("method: create - customer: {}", customer);

    return customer;
  }

  private Customer requireCustomerExisting(String mobileNumber) {
    return customerRepo
        .findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new DomainException(DomainCode.CREDENTIALS_INVALID,
            Map.of("mobileNumber", mobileNumber)));
  }

  private void requirePasswordMatching(Customer customer, String password) {
    if (!cryptoUtil.match(password, customer.getPassword())) {
      throw new DomainException(DomainCode.CREDENTIALS_INVALID,
          Map.of("mobileNumber", customer.getMobileNumber()));
    }
  }

  private String createToken(Customer customer) {
    return cryptoUtil.signCustomerToken(new CustomerToken()
        .setTokenId(UUID.randomUUID())
        .setCustomerId(customer.getId())
        .setMobileNumber(customer.getMobileNumber())
        .setName(customer.getName())
        .setScore(customer.getScore())
        .setExpiredAt(LocalDateTime.now().plus(TOKEN_MAX_AGE)));
  }

  @Override
  public String login(LoginCustomerCmd cmd) {
    log.info("method: login - cmd: {}", cmd);

    var customer = requireCustomerExisting(cmd.getMobileNumber());

    requirePasswordMatching(customer, cmd.getPassword());

    var token = createToken(customer);

    log.info("method: login");

    return token;
  }
}
