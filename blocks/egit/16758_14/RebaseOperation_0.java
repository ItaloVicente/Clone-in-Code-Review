		this(repository, ref, Operation.BEGIN, null);
	}

	public RebaseOperation(Repository repository, Ref ref,
			InteractiveHandler handler) {
		this(repository, ref, Operation.BEGIN, handler);
