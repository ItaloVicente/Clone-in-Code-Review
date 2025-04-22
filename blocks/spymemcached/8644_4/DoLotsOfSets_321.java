  public static void main(String[] args) throws Exception {
    MemcachedClient client =
        new MemcachedClient(new DefaultConnectionFactory(350000, 32768),
        AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
    long start = System.currentTimeMillis();
    byte[] toStore = new byte[26];
    Arrays.fill(toStore, (byte) 'a');
    for (int i = 0; i < 300000; i++) {
      client.set("k" + i, 300, toStore);
    }
    long added = System.currentTimeMillis();
    System.err.printf("Finished queuing in %sms%n", added - start);
    client.waitForQueues(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    long end = System.currentTimeMillis();
    System.err.printf("Completed everything in %sms (%sms to flush)%n", end
        - start, end - added);
    Map<String, Object> m = client.getBulk("k1", "k2", "k3", "k4", "k5",
        "k299999", "k299998", "k299997", "k299996");
    assert m.size() == 9 : "Expected 9 results, got " + m;
    client.shutdown();
  }
