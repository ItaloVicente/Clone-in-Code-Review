		final IEclipseContext executionContext = getExecutionContext();
		addParms(command, staticContext);
		executionContext.set(STATIC_CONTEXT, staticContext);
		push(executionContext);
		try {
			Command cmd = command.getCommand();
			cmd.setEnabled(executionContext);
			return cmd.isEnabled();
		} finally {
			pop();
			executionContext.remove(STATIC_CONTEXT);
		}
	}

	public boolean old_canExecute(ParameterizedCommand command, IEclipseContext staticContext) {
