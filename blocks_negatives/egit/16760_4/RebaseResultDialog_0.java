				SkipRebaseCommand skipCommand = new SkipRebaseCommand() {
					protected Repository getRepository(ExecutionEvent event) {
						return repo;
					}
				};
				execute(skipCommand);
