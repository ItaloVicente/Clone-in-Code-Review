	public RebaseOperation(Repository repository, Ref ref,
			InteractiveHandler interactiveHandler) {
		this.repository = repository;
		this.operation = Operation.BEGIN;
		this.interactiveHandler = interactiveHandler;
		this.ref = ref;
	}

