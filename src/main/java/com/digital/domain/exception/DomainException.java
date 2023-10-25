package com.digital.domain.exception;

import com.digital.domain.model.DomainCode;
import java.util.Map;

public class DomainException extends RuntimeException {

  private final DomainCode domainCode;

  private final Map<String, Object> moreInfo;

  public DomainException(DomainCode domainCode, Map<String, Object> moreInfo) {
    super(String.format("%s - moreInfo: %s", domainCode, moreInfo));
    this.domainCode = domainCode;
    this.moreInfo = moreInfo;
  }

  public DomainException(DomainCode domainCode, Map<String, Object> moreInfo, Throwable cause) {
    super(String.format("%s - moreInfo: %s", domainCode, moreInfo), cause);
    this.domainCode = domainCode;
    this.moreInfo = moreInfo;
  }

  public DomainCode getDomainCode() {
    return domainCode;
  }

  public Map<String, Object> getMoreInfo() {
    return moreInfo;
  }
}
