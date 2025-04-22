  public void testLibKetamaCompatTwo() {
    String[] servers = {"10.0.1.1:11211", "10.0.1.2:11211",
      "10.0.1.3:11211", "10.0.1.4:11211", "10.0.1.5:11211",
      "10.0.1.6:11211", "10.0.1.7:11211", "10.0.1.8:11211"};
    locator =
        new KetamaNodeLocator(Arrays.asList(mockNodes(servers)),
            HashAlgorithm.KETAMA_HASH);
