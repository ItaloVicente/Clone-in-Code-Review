	private final ProxyCallback pcb;

	/**
	 * Construct an optimized get starting with the given get operation.
	 */
	public OptimizedGetImpl(GetOperation firstGet) {
		super(Collections.<String>emptySet(), new ProxyCallback());
		pcb=(ProxyCallback)getCallback();
		addOperation(firstGet);
	}

	/**
	 * Add a new GetOperation to get.
	 */
	public void addOperation(GetOperation o) {
		pcb.addCallbacks(o);
		for(String k : o.getKeys()) {
			addKey(k);
			setVBucket(k, ((VBucketAware)o).getVBucket(k));
		}
	}

	public int size() {
		return pcb.numKeys();
	}

