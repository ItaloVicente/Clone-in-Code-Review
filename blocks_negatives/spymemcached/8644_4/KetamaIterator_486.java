    /**
     * Create a new KetamaIterator to be used by a client for an operation.
     *
     * @param k the key to iterate for
     * @param t the number of tries until giving up
     * @param ketamaNodes the continuum in the form of a TreeMap to be used when selecting a node
     * @param hashAlg the hash algorithm to use when selecting within the continuumq
     */
    protected KetamaIterator(final String k, final int t, TreeMap<Long, MemcachedNode> ketamaNodes, final HashAlgorithm hashAlg) {
	super();
	this.ketamaNodes = ketamaNodes;
	this.hashAlg = hashAlg;
	hashVal = hashAlg.hash(k);
	remainingTries = t;
	key = k;
    }
