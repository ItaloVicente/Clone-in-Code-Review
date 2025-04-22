		AbortRebaseCommand abortCommand = new AbortRebaseCommand() {
			protected Repository getRepository(ExecutionEvent event) {
				return currentRepository;
			}
		};
		executeRebaseOperation(abortCommand);
