    this(nodes, alg, KetamaNodeKeyFormatter.Format.SPYMEMCACHED, new HashMap<InetSocketAddress, Integer>());
  }

    public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg,
            KetamaNodeKeyFormatter.Format nodeKeyFormat,
            Map<InetSocketAddress, Integer> weights) {
    this(nodes, alg, weights, new DefaultKetamaNodeLocatorConfiguration(new KetamaNodeKeyFormatter(nodeKeyFormat)));
