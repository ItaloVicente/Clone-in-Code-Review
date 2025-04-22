  @Override
  public void initialize() {
    try {
      SaslClient sc =
          Sasl.createSaslClient(mech, null, "memcached", serverName,
              props, cbh);
