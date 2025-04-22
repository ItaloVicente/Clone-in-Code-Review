    URL specURL = resource.toURL();
    URLConnection connection = specURL.openConnection();
    connection.setRequestProperty("Accept", "application/json");
    connection.setRequestProperty("user-agent", "spymemcached vbucket client");
    connection.setRequestProperty("X-memcachekv-Store-Client-"
      + "Specification-Version", CLIENT_SPEC_VER);
    if (restUsr != null) {
      try {
        connection.setRequestProperty("Authorization",
            buildAuthHeader(restUsr, restPwd));
      } catch (UnsupportedEncodingException ex) {
        throw new IOException("Could not encode specified credentials for "
          + "HTTP request.", ex);
      }
    }
    return connection;
  }
