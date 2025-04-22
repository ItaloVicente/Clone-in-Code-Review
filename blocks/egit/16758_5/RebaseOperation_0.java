		this.handler = null;
	}

	public RebaseOperation(Repository repository, Ref ref,
			InteractiveHandler handler) {
		this.repository = repository;
		this.operation = Operation.BEGIN;
		this.ref = ref;
		this.handler = handler;
