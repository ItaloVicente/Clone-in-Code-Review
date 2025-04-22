  EXISTS,
  OBSERVE_ERROR_IN_ARGS,
  OBSERVE_MODIFIED,
  OBSERVE_TIMEOUT;

  private Long casValue=null;

  public void setCas(long cas) {
    if (casValue == null) {
      casValue = cas;
    } else {
      throw new IllegalArgumentException("CAS Value is already set to "
              + casValue);
    }
  }

  public long getCas() {
    return casValue;
  }

