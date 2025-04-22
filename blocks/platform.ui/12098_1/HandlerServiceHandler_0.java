	@Override
	public boolean isEnabled() {
		ExecutionContexts contexts = HandlerServiceImpl.peek();
		IEclipseContext executionContext = getExecutionContext(contexts);
		if (executionContext == null) {
			return super.isEnabled();
		}
		IEclipseContext staticContext = getStaticContext(contexts);
		Object handler = HandlerServiceImpl.lookUpHandler(executionContext, commandId);
		if (handler == null) {
			setBaseEnabled(false);
			return super.isEnabled();
		}
		Boolean result = (Boolean) ContextInjectionFactory.invoke(handler, CanExecute.class,
				executionContext, staticContext, Boolean.TRUE);
		setBaseEnabled(result.booleanValue());
		return super.isEnabled();
	}

