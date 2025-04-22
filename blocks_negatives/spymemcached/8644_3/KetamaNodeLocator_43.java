
	private volatile TreeMap<Long, MemcachedNode> ketamaNodes;
	final Collection<MemcachedNode> allNodes;

	final HashAlgorithm hashAlg;
	final KetamaNodeLocatorConfiguration config;


	/**
	 * Create a new KetamaNodeLocator using specified nodes and the specifed hash algorithm.
	 *
	 * @param nodes The List of nodes to use in the Ketama consistent hash continuum
	 * @param alg The hash algorithm to use when choosing a node in the Ketama consistent hash continuum
	 */
	public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg) {
        this(nodes, alg, new DefaultKetamaNodeLocatorConfiguration());
	}

	/**
	 * Create a new KetamaNodeLocator using specified nodes and the specifed hash algorithm and configuration.
	 *
	 * @param nodes The List of nodes to use in the Ketama consistent hash continuum
	 * @param alg The hash algorithm to use when choosing a node in the Ketama consistent hash continuum
	 * @param conf
	 */
	public KetamaNodeLocator(List<MemcachedNode> nodes, HashAlgorithm alg, KetamaNodeLocatorConfiguration conf) {
		super();
		allNodes = nodes;
		hashAlg = alg;
		config = conf;

		setKetamaNodes(nodes);

