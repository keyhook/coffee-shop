package com.digital.infra.springsecurity;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Data
@Configuration
@ConfigurationProperties("digial.infra.springsecurity")
public class SpringSecurityConfig {

  private Set<String> internalSecuredPaths = Set.of("/internal");

  @Getter(AccessLevel.NONE)
  private String jwtSecret = "VBSzNDkJNFKBfS4+1c37If/w06w5bVhUK9brL4sqJvA=";

  @Bean
  public Argon2PasswordEncoder argon2PasswordEncoder() {
    return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
  }

  @Bean
  public MACSigner macSigner() throws KeyLengthException {
    return new MACSigner(jwtSecret);
  }

  @Bean
  public MACVerifier macVerifier() throws JOSEException {
    return new MACVerifier(jwtSecret);
  }

  @Bean
  public JWSHeader jwsHeader() {
    return new JWSHeader(JWSAlgorithm.HS256);
  }
}
