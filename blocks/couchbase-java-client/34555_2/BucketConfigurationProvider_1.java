    connection.addObserver(new ConnectionObserver() {
      @Override
      public void connectionEstablished(SocketAddress sa, int reconnectCount) {
        getLogger().debug("Carrier Config Connection established to " + sa);
      }

      @Override
      public void connectionLost(SocketAddress sa) {
        getLogger().debug("Carrier Config Connection lost from " + sa);
        CouchbaseConnection conn = binaryConnection.getAndSet(null);
        try {
          conn.shutdown();
        } catch (IOException e) {
          getLogger().debug("Could not shut down Carrier Config Connection", e);
        }
        signalOutdated();
      }
    });
