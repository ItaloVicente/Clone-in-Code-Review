    this(nodes, alg, new HashMap<InetSocketAddress, Integer>(), conf);
  }

    public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg,
            Map<InetSocketAddress, Integer> nodeWeights,
            KetamaNodeLocatorConfiguration configuration) {
