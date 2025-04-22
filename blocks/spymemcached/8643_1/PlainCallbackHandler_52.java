  private final String username;
  private final char[] password;

  public PlainCallbackHandler(String u, String p) {
    username = u;
    password = p.toCharArray();
  }

  public void handle(Callback[] callbacks) throws IOException,
      UnsupportedCallbackException {
    for (Callback cb : callbacks) {
      if (cb instanceof TextOutputCallback) {
        throw new UnsupportedCallbackException(cb);
      } else if (cb instanceof NameCallback) {
        NameCallback nc = (NameCallback) cb;
        nc.setName(username);
      } else if (cb instanceof PasswordCallback) {
        PasswordCallback pc = (PasswordCallback) cb;
        pc.setPassword(password);
      } else {
        throw new UnsupportedCallbackException(cb);
      }
    }
  }
