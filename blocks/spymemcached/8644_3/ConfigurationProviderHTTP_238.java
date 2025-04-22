  protected static String buildAuthHeader(String username, String password)
    throws UnsupportedEncodingException {
    StringBuilder clearText = new StringBuilder(username);
    clearText.append(':');
    if (password != null) {
      clearText.append(password);
