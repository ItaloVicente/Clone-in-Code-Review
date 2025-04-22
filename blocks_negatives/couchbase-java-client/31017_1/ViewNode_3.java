  public boolean writeOp(HttpOperation op) {
    AsyncConnectionRequest connRequest = connMgr.requestConnection();
    try {
      connRequest.waitFor();
    } catch (InterruptedException e) {
      getLogger().warn(
          "Interrupted while trying to get a connection.");
      connRequest.cancel();
      return false;
    }
