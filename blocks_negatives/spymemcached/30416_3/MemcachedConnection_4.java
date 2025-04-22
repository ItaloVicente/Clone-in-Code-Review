        if (ch != null && !ch.isConnected() && !ch.isConnectionPending()) {
          try {
            ch.close();
          } catch (IOException x) {
            getLogger().error("Exception closing channel: %s", qa, x);
          }
        }
