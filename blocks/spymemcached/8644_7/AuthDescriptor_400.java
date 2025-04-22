  private final String[] mechs;
  private final CallbackHandler cbh;
  private int authAttempts;
  private int allowedAuthAttempts;

  public AuthDescriptor(String[] m, CallbackHandler h) {
    mechs = m;
    cbh = h;
    authAttempts = 0;
    String authThreshhold =
        System.getProperty("net.spy.memcached.auth.AuthThreshold");
    if (authThreshhold != null) {
      allowedAuthAttempts = Integer.parseInt(authThreshhold);
    } else {
      allowedAuthAttempts = -1; // auth forever
    }
  }

  public static AuthDescriptor typical(String u, String p) {
    return new AuthDescriptor(new String[] { "CRAM-MD5", "PLAIN" },
        new PlainCallbackHandler(u, p));
  }

  public boolean authThresholdReached() {
    if (allowedAuthAttempts < 0) {
      return false; // negative value means auth forever
    } else if (authAttempts >= allowedAuthAttempts) {
      return true;
    } else {
      authAttempts++;
      return false;
    }
  }

  public String[] getMechs() {
    return mechs;
  }

  public CallbackHandler getCallback() {
    return cbh;
  }
