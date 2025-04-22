      @Override
      public FailureMode getFailureMode() {
        return FailureMode.Retry;
      }
    }, AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
  }
