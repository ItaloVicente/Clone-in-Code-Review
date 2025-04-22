  private volatile TreeMap<Long, MemcachedNode> ketamaNodes;
  private final Collection<MemcachedNode> allNodes;

  private final HashAlgorithm hashAlg;
  private final KetamaNodeLocatorConfiguration config;

  public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg) {
    this(nodes, alg, new DefaultKetamaNodeLocatorConfiguration());
  }

  public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg,
      KetamaNodeLocatorConfiguration conf) {
    super();
    allNodes = nodes;
    hashAlg = alg;
    config = conf;
    setKetamaNodes(nodes);
  }

  private KetamaNodeLocator(TreeMap<Long, MemcachedNode> smn,
      Collection<MemcachedNode> an, HashAlgorithm alg,
      KetamaNodeLocatorConfiguration conf) {
    super();
    ketamaNodes = smn;
    allNodes = an;
    hashAlg = alg;
    config = conf;
  }

  public Collection<MemcachedNode> getAll() {
    return allNodes;
  }

  public MemcachedNode getPrimary(final String k) {
    MemcachedNode rv = getNodeForKey(hashAlg.hash(k));
    assert rv != null : "Found no node for key " + k;
    return rv;
  }

  long getMaxKey() {
    return getKetamaNodes().lastKey();
  }

  MemcachedNode getNodeForKey(long hash) {
    final MemcachedNode rv;
    if (!ketamaNodes.containsKey(hash)) {
      SortedMap<Long, MemcachedNode> tailMap = getKetamaNodes().tailMap(hash);
      if (tailMap.isEmpty()) {
        hash = getKetamaNodes().firstKey();
      } else {
        hash = tailMap.firstKey();
      }
