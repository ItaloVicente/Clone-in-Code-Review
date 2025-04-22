  protected void setupNodes(HashAlgorithm alg, int n) {
    super.setupNodes(n);
    for (int i = 0; i < nodeMocks.length; i++) {
      nodeMocks[i].expects(atLeastOnce()).method("getSocketAddress")
          .will(returnValue(InetSocketAddress.createUnresolved("127.0.0.1",
          10000 + i)));
    }
