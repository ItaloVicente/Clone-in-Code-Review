    NHttpClientConnection conn = connRequest.getConnection();
    if (conn == null) {
      getLogger().debug("Failed to obtain connection on node " + this.addr);
      connRequest.cancel();
      return false;
    } else {
      if (!user.equals("default")) {
        try {
          op.addAuthHeader(HttpUtil.buildAuthHeader(user, pass));
        } catch (UnsupportedEncodingException ex) {
          getLogger().error("Could not create auth header for request, "
            + "could not encode credentials into base64. Canceling op."
            + op, ex);
          op.cancel();
          connRequest.cancel();
        }
