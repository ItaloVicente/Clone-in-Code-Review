		InteractiveHandler handler = interactive ? RebaseInteractiveHandler.INSTANCE
				: null;
		RebaseOperation operation = new RebaseOperation(repository, ref,
				handler);
		operation.setPreserveMerges(preserveMerges);
		return operation;
