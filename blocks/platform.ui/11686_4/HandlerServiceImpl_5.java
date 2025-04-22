		return null;
	}

	public Object old_executeHandler(ParameterizedCommand command, IEclipseContext staticContext) {
		String commandId = command.getId();
		final IEclipseContext executionContext = getExecutionContext();
		addParms(command, staticContext);
