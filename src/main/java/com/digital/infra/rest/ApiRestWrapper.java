package com.digital.infra.rest;

import com.digital.domain.model.DomainCode;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(basePackageClasses = RestMarker.class)
public class ApiRestWrapper implements ResponseBodyAdvice<Object> {

  private final MessageSource messageSource;

  public ApiRestWrapper(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    if (body instanceof ApiResp) {
      return body;
    }

    return new ApiResp()
        .setCode(DomainCode.OK.getUniversalCode())
        .setMsg(messageSource.getMessage(DomainCode.OK.getValue(), null, Locale.getDefault()))
        .setData(body);
  }

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }
}
