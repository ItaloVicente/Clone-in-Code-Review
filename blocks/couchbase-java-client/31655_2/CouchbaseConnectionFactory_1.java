  public long getObsTimeout() {
    return DEFAULT_OBS_TIMEOUT;
  }

  public int getObsPollMax() {
    return new Double(
      Math.ceil((double) getObsTimeout() / getObsPollInterval())
    ).intValue();
