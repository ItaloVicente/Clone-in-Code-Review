		final IEclipseContext executionContext = getExecutionContext();
		addParms(command, staticContext);
		push(executionContext, staticContext);
		try {
			return command.executeWithChecks(null, peek());
		} catch (ExecutionException e) {
			staticContext.set(HANDLER_EXCEPTION, e);
		} catch (NotDefinedException e) {
			staticContext.set(HANDLER_EXCEPTION, e);
		} catch (NotEnabledException e) {
			staticContext.set(HANDLER_EXCEPTION, e);
		} catch (NotHandledException e) {
			staticContext.set(HANDLER_EXCEPTION, e);
		} finally {
			pop();
		}
		return null;
	}

	public Object old_executeHandler(ParameterizedCommand command, IEclipseContext staticContext) {
