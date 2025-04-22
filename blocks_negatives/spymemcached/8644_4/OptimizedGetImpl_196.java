	/**
	 * Construct an optimized get starting with the given get operation.
	 */
	public OptimizedGetImpl(GetOperation firstGet) {
		super(new HashSet<String>(), new ProxyCallback());
		pcb=(ProxyCallback)getCallback();
		addOperation(firstGet);
	}
