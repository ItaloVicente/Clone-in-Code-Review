    CouchbaseConnection oldBinaryConnection = binaryConnection.getAndSet(null);
    if (oldBinaryConnection != null) {
      try {
        oldBinaryConnection.shutdown();
      } catch (IOException e) {
        getLogger().warn("Failed to shutdown old binary config connection.", e);
      }
    }
