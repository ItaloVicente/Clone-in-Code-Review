		this(repository, null, operation, null);
	}

	public RebaseOperation(Repository repository, Operation operation,
			InteractiveHandler handler) {
		this(repository, null, operation, handler);
	}

	private RebaseOperation(Repository repository, Ref ref,
			Operation operation, InteractiveHandler handler) {
