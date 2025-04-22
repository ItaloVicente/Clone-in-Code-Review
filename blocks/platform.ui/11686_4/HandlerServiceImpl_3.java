		final IEclipseContext executionContext = getExecutionContext();
		addParms(command, staticContext);
		push(executionContext, staticContext);
		try {
			Command cmd = command.getCommand();
			cmd.setEnabled(peek());
			return cmd.isEnabled();
		} finally {
			pop();
		}
	}

	public boolean old_canExecute(ParameterizedCommand command, IEclipseContext staticContext) {
