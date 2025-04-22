    for (InetSocketAddress addr : addrs) {
      try {
        ViewNode node = connFactory.createViewNode(addr, this);
        node.init();
        nodeList.add(node);
      } catch(Exception ex) {
        throw new RuntimeException("Could not connect to View node.", ex);
      }
