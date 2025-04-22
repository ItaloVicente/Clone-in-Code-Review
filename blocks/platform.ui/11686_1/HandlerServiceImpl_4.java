		final IEclipseContext executionContext = getExecutionContext();
		addParms(command, staticContext);
		executionContext.set(STATIC_CONTEXT, staticContext);
		push(executionContext);
		try {
			return command.executeWithChecks(null, executionContext);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (NotDefinedException e) {
			e.printStackTrace();
		} catch (NotEnabledException e) {
			e.printStackTrace();
		} catch (NotHandledException e) {
			e.printStackTrace();
		} finally {
			pop();
			executionContext.remove(STATIC_CONTEXT);
		}
		return null;
	}

	public Object old_executeHandler(ParameterizedCommand command, IEclipseContext staticContext) {
