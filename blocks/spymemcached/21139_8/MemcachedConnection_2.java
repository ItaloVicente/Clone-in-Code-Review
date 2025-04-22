      try {
        qa.getChannel().socket().close();
      } catch (IOException e) {
        getLogger().warn("IOException trying to close a socket", e);
        getLogger().fatal("Retry won't be attempted on node \"" + qa + "\". "
          + "Client is in an unexpected state and should be terminated.");
      }
