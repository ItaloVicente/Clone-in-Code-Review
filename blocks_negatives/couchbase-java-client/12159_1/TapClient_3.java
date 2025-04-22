    final TapConnectionProvider conn;
    if (vBucketAware) {
      conn = new TapConnectionProvider(baseList, bucketName, usr, pwd);
    } else {
      conn = new TapConnectionProvider(addrs);
    }
