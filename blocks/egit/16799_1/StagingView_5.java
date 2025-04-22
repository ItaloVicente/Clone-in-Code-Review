		SkipRebaseCommand skipCommand = new SkipRebaseCommand() {
			@Override
			protected Repository getRepository(ExecutionEvent event) {
				return currentRepository;
			}
		};
		executeRebaseOperation(skipCommand);
