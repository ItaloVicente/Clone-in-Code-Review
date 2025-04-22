  @Override
  protected String getExpectedVersionSource() {
    if (TestConfig.defaultToIPV4()) {
      return String.valueOf(new InetSocketAddress(TestConfig.IPV4_ADDR, 11211));
    }
    return String.valueOf(new InetSocketAddress(TestConfig.IPV6_ADDR, 11211));
  }
