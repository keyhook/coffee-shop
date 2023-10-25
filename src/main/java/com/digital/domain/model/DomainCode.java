package com.digital.domain.model;

public enum DomainCode {

  OK("0000"),

  UNKNOWN_ERROR("1000"),

  INVALID_INPUT_FIELD("1001"),

  MISSING_REQUEST_PARAMETER("1002"),

  MISSING_REQUEST_HEADER("1003"),

  HTTP_MESSAGE_NOT_READABLE("1004"),

  METHOD_ARGUMENT_TYPE_MISMATCH("1005"),

  HTTP_MEDIA_TYPE_NOT_SUPPORTED("1006"),

  HTTP_REQUEST_METHOD_NOT_SUPPORTED("1007"),

  // Begin custom domain code
  CREDENTIALS_INVALID("2000"),

  STATUS_INVALID("2001"),

  TOKEN_SIGN_FAILED("2002"),

  TOKEN_INVALID("2003"),

  ENTITY_NOT_FOUND("2004"),

  UNAUTHORIZED("2005"),

  QUEUE_COUNT_INVALID("2006"),

  QUEUE_SIZE_INVALID("2007"),

  COFFEES_INVALID("2008"),

  MENU_ITEM_INVALID("2009"),

  WORKING_TIME_INVALID("2010"),

  MOBILE_NUMBER_DUPLICATED("2011");

  private final String value;

  DomainCode(String value) {
    this.value = value;
  }

  public String getUniversalCode() {
    return String.format("COF%s", value);
  }

  public String getValue() {
    return value;
  }
}
