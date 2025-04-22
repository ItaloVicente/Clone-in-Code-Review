	private void execute(AbstractRebaseCommandHandler command) {
		try {
			command.execute(repo);
		} catch (ExecutionException e) {
			Activator.showError(e.getMessage(), e);
		}
	}

