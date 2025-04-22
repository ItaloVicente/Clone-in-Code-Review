	/* (non-Javadoc)
	 * @see net.spy.memcached.ConnectionFactory#createLocator(java.util.List)
	 */
	@Override
	public NodeLocator createLocator(List<MemcachedNode> nodes) {
		return new KetamaNodeLocator(nodes, getHashAlg());
	}
