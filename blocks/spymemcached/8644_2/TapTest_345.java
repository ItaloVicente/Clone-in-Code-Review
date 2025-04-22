  public void testBackfill() throws Exception {
    if (TestConfig.isMembase()) {
      TapClient tc =
          new TapClient(AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11210"));
      tc.tapBackfill(null, 5, TimeUnit.SECONDS);
      HashMap<String, Boolean> items = new HashMap<String, Boolean>();
      for (int i = 0; i < 25; i++) {
        client.set("key" + i, 0, "value" + i);
        items.put("key" + i + ",value" + i, new Boolean(false));
      }
