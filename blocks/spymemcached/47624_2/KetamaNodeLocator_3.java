    this(nodes, alg, KetamaNodeKeyFormat.SPYMEMCACHED, new HashMap<InetSocketAddress, Integer>());
  }

    public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg,
            KetamaNodeKeyFormat.Format nodeKeyFormat,
            Map<InetSocketAddress, Integer> weights) {
    this(nodes, alg, weights, new DefaultKetamaNodeLocatorConfiguration(new KetamaNodeKeyFormat(nodeKeyFormat)));
