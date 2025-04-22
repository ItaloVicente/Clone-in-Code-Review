		CloneCommand command = new CloneCommand(url);
		try {
			command.execute(new ExecutionEvent());
		} catch (ExecutionException e) {
			Activator.logError(e.getLocalizedMessage(), e);
		}
