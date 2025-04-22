    try {
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
            HttpContext context = conn.getContext();
            RequestHandle handle = new RequestHandle(connMgr, conn);
            context.setAttribute("request-handle", handle);
            context.setAttribute("operation", op);
            conn.requestOutput();
          }
