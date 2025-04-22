    rv = getKetamaNodes().get(hash);
    return rv;
  }

  public Iterator<MemcachedNode> getSequence(String k) {
    return new KetamaIterator(k, 7, getKetamaNodes(), hashAlg);
  }

  public NodeLocator getReadonlyCopy() {
    TreeMap<Long, MemcachedNode> smn =
        new TreeMap<Long, MemcachedNode>(getKetamaNodes());
    Collection<MemcachedNode> an =
        new ArrayList<MemcachedNode>(allNodes.size());

    for (Map.Entry<Long, MemcachedNode> me : smn.entrySet()) {
      me.setValue(new MemcachedNodeROImpl(me.getValue()));
    }
    for (MemcachedNode n : allNodes) {
      an.add(new MemcachedNodeROImpl(n));
