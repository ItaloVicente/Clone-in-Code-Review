    NHttpClientConnection conn = connRequest.getConnection();
    if (conn == null) {
      getLogger().error("Failed to obtain connection. Cancelling op");
      op.cancel();
    } else {
      if (!user.equals("default")) {
        try {
          op.addAuthHeader(HttpUtil.buildAuthHeader(user, pass));
        } catch (UnsupportedEncodingException ex) {
          getLogger().error("Could not create auth header for request, "
            + "could not encode credentials into base64. Canceling op."
            + op, ex);
          op.cancel();
        }
      }
      HttpContext context = conn.getContext();
      RequestHandle handle = new RequestHandle(connMgr, conn);
      context.setAttribute("request-handle", handle);
      context.setAttribute("operation", op);
      conn.requestOutput();
    }
