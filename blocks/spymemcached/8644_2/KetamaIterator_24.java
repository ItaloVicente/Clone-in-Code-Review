  protected KetamaIterator(final String k, final int t,
      TreeMap<Long, MemcachedNode> ketamaNodes, final HashAlgorithm hashAlg) {
    super();
    this.ketamaNodes = ketamaNodes;
    this.hashAlg = hashAlg;
    hashVal = hashAlg.hash(k);
    remainingTries = t;
    key = k;
  }
