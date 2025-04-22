		ContinueRebaseCommand continueCommand = new ContinueRebaseCommand() {
			@Override
			protected Repository getRepository(ExecutionEvent event) {
				return currentRepository;
			}
		};
		executeRebaseOperation(continueCommand);
