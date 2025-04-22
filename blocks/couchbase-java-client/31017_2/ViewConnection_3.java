    HttpCoreContext coreContext = HttpCoreContext.create();

    if (viewNodes.isEmpty()) {
      getLogger().error("No server connections. Cancelling op.");
      op.cancel();
    } else {
      if (!user.equals("default")) {
        try {
          op.addAuthHeader(HttpUtil.buildAuthHeader(user, password));
        } catch (UnsupportedEncodingException ex) {
          getLogger().error("Could not create auth header for request, "
            + "could not encode credentials into base64. Canceling op."
            + op, ex);
          op.cancel();
          return;
        }
