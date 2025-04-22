		try {
			Boolean result = ((Boolean) ContextInjectionFactory.invoke(handler, CanExecute.class,
					executionContext, staticContext, Boolean.TRUE));
			setBaseEnabled(result.booleanValue());
		} catch (Exception e) {
			e.printStackTrace();
