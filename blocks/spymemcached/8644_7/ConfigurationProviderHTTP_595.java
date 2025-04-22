  private String readToString(URLConnection connection) throws IOException {
    BufferedReader reader = null;
    try {
      InputStream inStream = connection.getInputStream();
      if (connection instanceof java.net.HttpURLConnection) {
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        if (httpConnection.getResponseCode() == 403) {
          throw new IOException("Service does not accept the authentication "
            + "credentials: " + httpConnection.getResponseCode()
            + httpConnection.getResponseMessage());
        } else if (httpConnection.getResponseCode() >= 400) {
          throw new IOException("Service responded with a failure code: "
            + httpConnection.getResponseCode()
            + httpConnection.getResponseMessage());
