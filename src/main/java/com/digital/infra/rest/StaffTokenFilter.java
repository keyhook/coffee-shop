package com.digital.infra.rest;

import com.digital.app.helper.CryptoUtil;
import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import com.digital.infra.springsecurity.SpringSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class StaffTokenFilter extends OncePerRequestFilter {

  private static final String BEARER_SCHEME = "Bearer ";

  public static final String STAFF_TOKEN_ATTRIBUTE = "digital.staffToken";

  private final GlobalExceptionHandler globalExceptionHandler;

  private final CryptoUtil cryptoUtil;

  private final ObjectMapper objectMapper;

  private final SpringSecurityConfig springSecurityConfig;

  public StaffTokenFilter(
      GlobalExceptionHandler globalExceptionHandler,
      CryptoUtil cryptoUtil,
      ObjectMapper objectMapper,
      SpringSecurityConfig springSecurityConfig) {
    this.globalExceptionHandler = globalExceptionHandler;
    this.cryptoUtil = cryptoUtil;
    this.objectMapper = objectMapper;
    this.springSecurityConfig = springSecurityConfig;
  }

  private boolean securedPath(HttpServletRequest request) {
    return springSecurityConfig
        .getInternalSecuredPaths()
        .stream()
        .anyMatch(securedPath -> request.getRequestURI().startsWith(securedPath));
  }

  private void raiseTokenInvalidError(
      HttpServletRequest request,
      HttpServletResponse response,
      DomainException domainException) throws IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    var out = response.getOutputStream();
    objectMapper.writeValue(out, globalExceptionHandler.domainException(request, domainException));
    out.flush();
  }

  private void handleSecuredPaths(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    var tokenAsString = Optional
        .ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
        .filter(authorization -> authorization.startsWith(BEARER_SCHEME))
        .map(authorization -> authorization.substring(BEARER_SCHEME.length()));

    if (tokenAsString.isPresent()) {
      try {
        var token = cryptoUtil.verifyStaffToken(tokenAsString.get());
        token.requireNotExpired();

        request.setAttribute(STAFF_TOKEN_ATTRIBUTE, token);

        filterChain.doFilter(request, response);
      } catch (DomainException e) {
        raiseTokenInvalidError(request, response, e);
      } finally {
        request.removeAttribute(STAFF_TOKEN_ATTRIBUTE);
      }
    } else {
      raiseTokenInvalidError(request, response,
          new DomainException(DomainCode.TOKEN_INVALID, Map.of()));
    }
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (securedPath(request)) {
      handleSecuredPaths(request, response, filterChain);
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
