	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			return execute(event.getParameters());
		} catch (final org.eclipse.ui.commands.ExecutionException e) {
			throw new ExecutionException(e.getMessage(), e.getCause());
		}
	}

