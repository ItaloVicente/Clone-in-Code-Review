    if (supportedMechs == null || supportedMechs.length == 0) {
      getLogger().warn("Authentication failed to " + node.getSocketAddress()
        + ", got empty SASL auth mech list.");
      throw new IllegalStateException("Got empty SASL auth mech list.");
    }

