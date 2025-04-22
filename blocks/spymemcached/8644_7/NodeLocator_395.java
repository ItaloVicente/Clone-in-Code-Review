  MemcachedNode getPrimary(String k);

  Iterator<MemcachedNode> getSequence(String k);

  Collection<MemcachedNode> getAll();

  NodeLocator getReadonlyCopy();

  void updateLocator(final List<MemcachedNode> nodes, final Config conf);
