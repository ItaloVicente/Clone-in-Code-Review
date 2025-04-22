	private void executeRebaseOperation(AbstractRebaseCommandHandler command) {
		try {
			command.execute(currentRepository);
		} catch (ExecutionException e) {
			Activator.showError(e.getMessage(), e);
		}
