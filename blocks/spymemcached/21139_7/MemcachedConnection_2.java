      try {
        qa.getChannel().socket().close();
      } catch (IOException e) {
        getLogger().warn("IOException trying to close a socket", e);
        getLogger().warn("Retry won't be attempted on node " + qa);
      }
