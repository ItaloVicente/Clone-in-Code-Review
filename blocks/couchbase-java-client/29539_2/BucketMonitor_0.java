    if (channel == null) {
      bootstrap.releaseExternalResources();
      throw new ConnectionException("Could not establish a streaming connection to "
        + host + ":" + port);
    }

