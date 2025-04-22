      while ((op = writeQ.take()) != null) {
        if (!op.isTimedOut() && !op.isCancelled()) {
          AsyncConnectionRequest connRequest = connMgr.requestConnection();
          try {
            connRequest.waitFor();
          } catch (InterruptedException e) {
            getLogger().warn(
                "Interrupted while trying to get a connection."
                    + " Cancelling op");
            op.cancel();
            return;
          }

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
        }
      }
