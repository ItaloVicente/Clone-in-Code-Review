		if (!shouldRunAction())
			return;

        ExecutionEvent event = createExecutionEvent();

		try {
			this.handler.execute(event);
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
	}

	protected ExecutionEvent createExecutionEvent() {
		IServiceLocator locator = getServiceLocator();
