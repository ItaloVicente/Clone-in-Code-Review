    this(nodes, alg, KetamaNodeKeyFormat.SPYMEMCACHED, new HashMap<InetSocketAddress, Integer>());
  }

    public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg,
            KetamaNodeKeyFormat nodeKeyFormat,
            Map<InetSocketAddress, Integer> weights) {
    this(nodes, alg, weights, new DefaultKetamaNodeLocatorConfiguration(nodeKeyFormat));
