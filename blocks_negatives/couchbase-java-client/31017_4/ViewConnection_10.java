      for (ViewNode qa : shutdownNodes) {
        try {
          qa.shutdown();
        } catch (IOException e) {
          getLogger().error("Error shutting down connection to "
              + qa.getSocketAddress());
