  public void testSmallSet() {
    runThisManyNodes(3);
  }

  public void testLargeSet() {
    runThisManyNodes(100);
  }

  private void runThisManyNodes(final int totalNodes) {
    final String[] stringNodes = generateAddresses(totalNodes);

    List<MemcachedNode> smaller =
        createNodes(AddrUtil.getAddresses(stringNodes[0]));
    List<MemcachedNode> larger =
        createNodes(AddrUtil.getAddresses(stringNodes[1]));

    assertTrue(larger.containsAll(smaller));
    MemcachedNode oddManOut = larger.get(larger.size() - 1);
    assertFalse(smaller.contains(oddManOut));

    KetamaNodeLocator lgLocator =
        new KetamaNodeLocator(larger, HashAlgorithm.KETAMA_HASH);
    KetamaNodeLocator smLocator =
        new KetamaNodeLocator(smaller, HashAlgorithm.KETAMA_HASH);

    SortedMap<Long, MemcachedNode> lgMap = lgLocator.getKetamaNodes();
    SortedMap<Long, MemcachedNode> smMap = smLocator.getKetamaNodes();

    boolean failed = false;
    for (final Long key : smMap.keySet()) {
      final MemcachedNode largeNode = lgMap.get(key);
      final MemcachedNode smallNode = smMap.get(key);
      if (!largeNode.equals(smallNode)) {
        failed = true;
        System.out.println("---------------");
        System.out.println("Key: " + key);
        System.out.println("Small: " + smallNode.getSocketAddress());
        System.out.println("Large: " + largeNode.getSocketAddress());
      }
    }
    assertFalse(failed);

    for (final Map.Entry<Long, MemcachedNode> entry : lgMap.entrySet()) {
      final Long key = entry.getKey();
      final MemcachedNode node = entry.getValue();
      if (node.equals(oddManOut)) {
        final MemcachedNode newNode = smLocator.getNodeForKey(key);
        if (!smaller.contains(newNode)) {
          System.out.println("Error - " + key + " -> "
              + newNode.getSocketAddress());
          failed = true;
        }
      }
    }
    assertFalse(failed);
  }

  private String[] generateAddresses(final int maxSize) {
    final String[] results = new String[2];

    long now = new Date().getTime();
    int first = (int) ((now % 250) + 3);

    int second = (int) (((now / 250) % 250) + 3);

    String port = ":11211 ";
    int last = (int) ((now % 100) + 3);

    StringBuffer prefix = new StringBuffer();
    prefix.append(first);
    prefix.append(".");
    prefix.append(second);
    prefix.append(".1.");

    StringBuffer buf = new StringBuffer();
    for (int ix = 0; ix < maxSize - 1; ix++) {
      buf.append(prefix);
      buf.append(last + ix);
      buf.append(port);
    }

    results[0] = buf.toString();

    buf.append(prefix);
    buf.append(last + maxSize - 1);
    buf.append(port);

    results[1] = buf.toString();

    return results;
  }

  private List<MemcachedNode> createNodes(List<InetSocketAddress> addresses) {
    List<MemcachedNode> results = new ArrayList<MemcachedNode>();

    for (InetSocketAddress addr : addresses) {
      results.add(new MockMemcachedNode(addr));
    }

    return results;
  }
