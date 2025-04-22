    this(cf);
    connect(addrs);
  }

  public boolean isConnected() {
    return mconn != null;
  }

  protected void verifyConnected() {
    if(!isConnected()) {
      throw new IllegalStateException("Currently not connected, please "
        + "connect first.");
