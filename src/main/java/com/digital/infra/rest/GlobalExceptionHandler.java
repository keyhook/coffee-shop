package com.digital.infra.rest;

import com.digital.domain.exception.DomainException;
import com.digital.domain.model.DomainCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private final MessageSource messageSource;

  public GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(BindException.class)
  public ApiResp bindException(
      HttpServletRequest request,
      BindException e) {
    var fieldErrors = e
        .getFieldErrors()
        .stream()
        .map(this::toFieldErrorDto)
        .toList();

    log.warn("method: bindException - endpoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.INVALID_INPUT_FIELD.getUniversalCode())
        .setMsg(messageSource.getMessage(DomainCode.INVALID_INPUT_FIELD.name(), null,
            Locale.getDefault()))
        .setData(Map.of("fieldErrors", fieldErrors));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResp methodArgumentNotValidException(
      HttpServletRequest request,
      MethodArgumentNotValidException e) {
    var fieldErrors = e
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map(this::toFieldErrorDto)
        .toList();

    log.warn("method: methodArgumentNotValidException - endpoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.INVALID_INPUT_FIELD.getUniversalCode())
        .setMsg(messageSource.getMessage(DomainCode.INVALID_INPUT_FIELD.getValue(), null,
            Locale.getDefault()))
        .setData(Map.of("fieldErrors", fieldErrors));
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ApiResp httpMediaTypeNotSupportedException(
      HttpServletRequest request,
      HttpMediaTypeNotSupportedException e) {
    log.warn(
        "method: httpMediaTypeNotSupportedException - endPoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getUniversalCode())
        .setMsg(messageSource.getMessage(DomainCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED.name(),
            new Object[]{e.getContentType()}, Locale.getDefault()));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ApiResp httpMessageNotReadableException(
      HttpServletRequest request,
      HttpMessageNotReadableException e) {
    log.warn(
        "method: httpMessageNotReadableException - endPoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.HTTP_MESSAGE_NOT_READABLE.getUniversalCode())
        .setMsg(messageSource.getMessage(
            DomainCode.HTTP_MESSAGE_NOT_READABLE.getValue(),
            null,
            Locale.getDefault()));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ApiResp httpRequestMethodNotSupportedException(
      HttpServletRequest request,
      HttpRequestMethodNotSupportedException e) {
    log.warn(
        "method: httpRequestMethodNotSupportedException - endPoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getUniversalCode())
        .setMsg(messageSource.getMessage(
            DomainCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getValue(),
            new Object[]{e.getMethod()},
            Locale.getDefault()));
  }


  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ApiResp methodArgumentTypeMismatchException(
      HttpServletRequest request,
      MethodArgumentTypeMismatchException e) {
    log.warn(
        "method: methodArgumentTypeMismatchException - endPoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.METHOD_ARGUMENT_TYPE_MISMATCH.getUniversalCode())
        .setMsg(messageSource.getMessage(
            DomainCode.METHOD_ARGUMENT_TYPE_MISMATCH.getValue(),
            new Object[]{e.getName()},
            Locale.getDefault()));
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ApiResp missingRequestHeaderException(
      HttpServletRequest request,
      MissingRequestHeaderException e) {
    log.warn(
        "method: missingRequestHeaderException - endPoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.MISSING_REQUEST_HEADER.getUniversalCode())
        .setMsg(messageSource.getMessage(
            DomainCode.MISSING_REQUEST_HEADER.getValue(),
            new Object[]{e.getHeaderName()},
            Locale.getDefault()));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ApiResp missingServletRequestParameterException(
      HttpServletRequest request,
      MissingServletRequestParameterException e) {
    log.warn(
        "method: missingServletRequestParameterException - endPoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.MISSING_REQUEST_PARAMETER.getUniversalCode())
        .setMsg(messageSource.getMessage(
            DomainCode.MISSING_REQUEST_PARAMETER.getValue(),
            new Object[]{e.getMessage()},
            Locale.getDefault()));
  }

  @ExceptionHandler(DomainException.class)
  public ApiResp domainException(
      HttpServletRequest request,
      DomainException e) {
    log.warn("method: domainException - endpoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(e.getDomainCode().getUniversalCode())
        .setMsg(messageSource.getMessage(
            e.getDomainCode().getValue(),
            null,
            Locale.getDefault()))
        .setData(e.getMoreInfo());
  }

  @ExceptionHandler(Exception.class)
  public ApiResp exception(
      HttpServletRequest request,
      Exception e) {
    log.error("method: exception - endpoint: {} - queryString: {}",
        request.getRequestURI(),
        request.getQueryString(),
        e);

    return new ApiResp()
        .setCode(DomainCode.UNKNOWN_ERROR.getUniversalCode())
        .setMsg(messageSource.getMessage(
            DomainCode.UNKNOWN_ERROR.getValue(),
            null,
            Locale.getDefault()));
  }

  private FieldErrorDto toFieldErrorDto(FieldError fieldError) {
    return new FieldErrorDto()
        .setName(fieldError.getField())
        .setDesc(Objects.isNull(fieldError.getDefaultMessage()) ? "n/a"
            : fieldError.getDefaultMessage());
  }
}