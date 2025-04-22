		if (preExecute != null) {
			ContextInjectionFactory.invoke(preExecute, Execute.class, executionContext,
					staticContext, null);
		}
		Object handler = lookUpHandler(context, commandId);
		if (handler == null) {
			staticContext.set(NOT_HANDLED, Boolean.TRUE);
			return null;
