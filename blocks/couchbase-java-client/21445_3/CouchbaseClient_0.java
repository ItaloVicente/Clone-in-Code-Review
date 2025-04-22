
    if(cf.getVBucketConfig().getConfigType() == ConfigType.COUCHBASE) {
      List<InetSocketAddress> addrs =
        AddrUtil.getAddressesFromURL(cf.getVBucketConfig().getCouchServers());
      vconn = cf.createViewConnection(addrs);
    }
