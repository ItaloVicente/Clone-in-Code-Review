	public MultiGetOperationImpl(Collection<String> k, OperationCallback cb) {
		super(-1, -1, cb);
		for(String s : new HashSet<String>(k)) {
			addKey(s);
		}
	}
