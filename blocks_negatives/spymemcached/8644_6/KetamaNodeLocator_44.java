
	private KetamaNodeLocator(TreeMap<Long, MemcachedNode> smn,
			Collection<MemcachedNode> an, HashAlgorithm alg, KetamaNodeLocatorConfiguration conf) {
		super();
		ketamaNodes=smn;
		allNodes=an;
		hashAlg=alg;
        config=conf;
	}

	public Collection<MemcachedNode> getAll() {
		return allNodes;
	}

	public MemcachedNode getPrimary(final String k) {
		MemcachedNode rv=getNodeForKey(hashAlg.hash(k));
		assert rv != null : "Found no node for key " + k;
		return rv;
	}

	long getMaxKey() {
		return getKetamaNodes().lastKey();
	}

	MemcachedNode getNodeForKey(long hash) {
		final MemcachedNode rv;
		if(!ketamaNodes.containsKey(hash)) {
			SortedMap<Long, MemcachedNode> tailMap=getKetamaNodes().tailMap(hash);
			if(tailMap.isEmpty()) {
				hash=getKetamaNodes().firstKey();
			} else {
				hash=tailMap.firstKey();
			}
		}
		rv=getKetamaNodes().get(hash);
		return rv;
	}

	public Iterator<MemcachedNode> getSequence(String k) {
		return new KetamaIterator(k, 7, getKetamaNodes(), hashAlg);
	}

	public NodeLocator getReadonlyCopy() {
		TreeMap<Long, MemcachedNode> smn=new TreeMap<Long, MemcachedNode>(
			getKetamaNodes());
		Collection<MemcachedNode> an=
			new ArrayList<MemcachedNode>(allNodes.size());

		for(Map.Entry<Long, MemcachedNode> me : smn.entrySet()) {
			me.setValue(new MemcachedNodeROImpl(me.getValue()));
		}
		for(MemcachedNode n : allNodes) {
			an.add(new MemcachedNodeROImpl(n));
		}

		return new KetamaNodeLocator(smn, an, hashAlg, config);
	}

	@Override
	public void updateLocator(List<MemcachedNode> nodes, Config conf) {
		setKetamaNodes(nodes);
	}

    /**
     * @return the ketamaNodes
     */
    protected TreeMap<Long, MemcachedNode> getKetamaNodes() {
	return ketamaNodes;
