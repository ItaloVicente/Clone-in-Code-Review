  protected static String buildAuthHeader(String username, String password)
    throws UnsupportedEncodingException {
    StringBuilder clearText = new StringBuilder(username);
    clearText.append(':');
    if (password != null) {
      clearText.append(password);
    }
    String headerResult;
    headerResult = "Basic "
      + Base64.encodeBase64String(clearText.toString().getBytes("UTF-8"));
    return headerResult;
  }
