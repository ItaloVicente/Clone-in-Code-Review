    cp = cf.getConfigurationProvider();
    cp.subscribe(cf.getBucketName(), this);
  }

  protected void addTapAckOp(MemcachedNode node, Operation op) {
    super.addTapAckOp(node, op);
