package com.digital.infra.springsecurity;

import com.digital.app.helper.CryptoUtil;
import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.domain.model.customer.CustomerToken;
import com.digital.domain.model.staff.Staff.Type;
import com.digital.domain.model.staff.StaffToken;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityCryptoUtil implements CryptoUtil {

  private final PasswordEncoder passwordEncoder;

  private final JWSSigner jwsSigner;

  private final JWSVerifier jwsVerifier;

  private final JWSHeader jwsHeader;

  public SpringSecurityCryptoUtil(
      PasswordEncoder passwordEncoder,
      JWSSigner jwsSigner,
      JWSVerifier jwsVerifier,
      JWSHeader jwsHeader) {
    this.passwordEncoder = passwordEncoder;
    this.jwsSigner = jwsSigner;
    this.jwsVerifier = jwsVerifier;
    this.jwsHeader = jwsHeader;
  }

  @Override
  public boolean match(String plainText, String hashedText) {
    return passwordEncoder.matches(plainText, hashedText);
  }

  @Override
  public String hash(String plainText) {
    return passwordEncoder.encode(plainText);
  }

  @Override
  public String signStaffToken(StaffToken staffToken) {
    try {
      var jwtClaimsSet = new JWTClaimsSet
          .Builder()
          .jwtID(staffToken.getTokenId().toString())
          .subject(staffToken.getStaffId().toString())
          .claim(StaffToken.STAFF_TYPE_KEY, staffToken.getStaffType().name())
          .claim(StaffToken.SHOP_ID_KEY, staffToken.getShopId().toString())
          .expirationTime(Date.from(staffToken.getExpiredAt().toInstant(ZoneOffset.UTC)))
          .build();

      var signedJwt = new SignedJWT(jwsHeader, jwtClaimsSet);

      signedJwt.sign(jwsSigner);

      return signedJwt.serialize();
    } catch (JOSEException e) {
      throw new DomainException(DomainCode.TOKEN_SIGN_FAILED, Map.of("token", staffToken), e);
    }
  }

  @Override
  public StaffToken verifyStaffToken(String tokenAsString) {
    try {
      var signedJwt = SignedJWT.parse(tokenAsString);

      if (signedJwt.verify(jwsVerifier)) {
        return new StaffToken()
            .setTokenId(UUID.fromString(signedJwt.getJWTClaimsSet().getJWTID()))
            .setStaffId(UUID.fromString(signedJwt.getJWTClaimsSet().getSubject()))
            .setStaffType(Type.valueOf(signedJwt.getJWTClaimsSet().getStringClaim(StaffToken.STAFF_TYPE_KEY)))
            .setShopId(UUID.fromString(signedJwt.getJWTClaimsSet().getStringClaim(StaffToken.SHOP_ID_KEY)))
            .setExpiredAt(signedJwt
                .getJWTClaimsSet()
                .getExpirationTime()
                .toInstant()
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime());
      }

      throw new DomainException(DomainCode.TOKEN_INVALID, Map.of("token", tokenAsString));
    } catch (JOSEException | ParseException e) {
      throw new DomainException(DomainCode.TOKEN_INVALID, Map.of("token", tokenAsString), e);
    }
  }

  @Override
  public String signCustomerToken(CustomerToken customerToken) {
    try {
      var jwtClaimsSet = new JWTClaimsSet
          .Builder()
          .jwtID(customerToken.getTokenId().toString())
          .subject(customerToken.getCustomerId().toString())
          .claim(CustomerToken.MOBILE_NUMBER_KEY, customerToken.getMobileNumber())
          .claim(CustomerToken.NAME_KEY, customerToken.getName())
          .claim(CustomerToken.SCORE_KEY, customerToken.getScore())
          .expirationTime(Date.from(customerToken.getExpiredAt().toInstant(ZoneOffset.UTC)))
          .build();

      var signedJwt = new SignedJWT(jwsHeader, jwtClaimsSet);

      signedJwt.sign(jwsSigner);

      return signedJwt.serialize();
    } catch (JOSEException e) {
      throw new DomainException(DomainCode.TOKEN_SIGN_FAILED, Map.of("token", customerToken), e);
    }
  }

  @Override
  public CustomerToken verifyCustomerToken(String tokenAsString) {
    try {
      var signedJwt = SignedJWT.parse(tokenAsString);

      if (signedJwt.verify(jwsVerifier)) {
        return new CustomerToken()
            .setTokenId(UUID.fromString(signedJwt.getJWTClaimsSet().getJWTID()))
            .setCustomerId(UUID.fromString(signedJwt.getJWTClaimsSet().getSubject()))
            .setExpiredAt(signedJwt
                .getJWTClaimsSet()
                .getExpirationTime()
                .toInstant()
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime());
      }

      throw new DomainException(DomainCode.TOKEN_INVALID, Map.of("token", tokenAsString));
    } catch (JOSEException | ParseException e) {
      throw new DomainException(DomainCode.TOKEN_INVALID, Map.of("token", tokenAsString), e);
    }
  }
}
