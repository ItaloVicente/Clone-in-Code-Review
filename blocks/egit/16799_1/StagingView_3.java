	private void executeRebaseOperation(AbstractRebaseCommandHandler command) {
		try {
			command.execute(null);
		} catch (ExecutionException e) {
			Activator.showError(e.getMessage(), e);
		}
