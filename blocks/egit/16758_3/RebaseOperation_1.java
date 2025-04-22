		this.handler = null;
	}

	public RebaseOperation(Repository repository, Operation operation,
			InteractiveHandler handler) {
		this.repository = repository;
		this.operation = operation;
		this.ref = null;
		this.handler = handler;
