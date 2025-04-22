				AbortRebaseCommand abortCommand = new AbortRebaseCommand() {
					protected Repository getRepository(ExecutionEvent event) {
						return repo;
					}
				};
				execute(abortCommand);
