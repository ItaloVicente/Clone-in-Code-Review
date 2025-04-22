  @Override
  public void afterPropertiesSet() throws Exception {
    client = new MemcachedClient(connectionFactoryBuilder.build(),
            AddrUtil.getAddresses(servers));
  }

  @Override
  public void destroy() throws Exception {
    if(shutdownTimeoutSeconds > 0) {
      client.shutdown(shutdownTimeoutSeconds, TimeUnit.SECONDS);
    } else {
      client.shutdown();
    }
  }

