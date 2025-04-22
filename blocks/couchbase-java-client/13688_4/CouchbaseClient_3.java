    List<InetSocketAddress> addrs =
      AddrUtil.getAddressesFromURL(cf.getVBucketConfig().getCouchServers());

    getLogger().info(MODE_ERROR);
    vconn = cf.createViewConnection(addrs);
    cf.getConfigurationProvider().subscribe(cf.getBucketName(), this);
