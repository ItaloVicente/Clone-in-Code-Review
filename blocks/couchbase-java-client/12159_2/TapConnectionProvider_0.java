  public TapConnectionProvider(CouchbaseConnectionFactory cf)
    throws IOException, ConfigurationException{
    super(cf, AddrUtil.getAddresses(cf.getVBucketConfig().getServers()));
    cf.getConfigurationProvider().subscribe(cf.getBucketName(), this);
  }

  protected void addOp(final Operation op) {
    conn.enqueueOperation("TStream", op);
