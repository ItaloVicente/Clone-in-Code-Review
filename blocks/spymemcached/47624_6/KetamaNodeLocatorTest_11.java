
  public void testLibKetamaWeightedCompatLibmemcached() {
      String[] servers = {"127.0.0.1:11211", "127.0.0.1:11212"};
      List<MemcachedNode> nodes = Arrays.asList(mockNodes(servers));
      Map<InetSocketAddress, Integer> weights = new HashMap<InetSocketAddress, Integer>();
      weights.put((InetSocketAddress) nodes.get(0).getSocketAddress(), 8);
      weights.put((InetSocketAddress) nodes.get(1).getSocketAddress(), 8);

      locator = new KetamaNodeLocator(nodes, DefaultHashAlgorithm.KETAMA_HASH,
              KetamaNodeKeyFormatter.Format.LIBMEMCACHED, weights);

      String[][] exp = { { "key1", "localhost/127.0.0.1:11212" }, { "key2", "localhost/127.0.0.1:11211" },
              { "key3", "localhost/127.0.0.1:11212" }, { "key4", "localhost/127.0.0.1:11212" } };

      for (String[] s : exp) {
          String k = s[0];
          String server = s[1];
          MemcachedNode n = locator.getPrimary(k);
          assertEquals(server, n.getSocketAddress().toString());
      }
  }

