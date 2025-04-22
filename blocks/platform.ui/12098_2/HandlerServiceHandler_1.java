		IEclipseContext staticContext = getStaticContext(evaluationContext);
		if (staticContext == null) {
			staticContext = EclipseContextFactory.create();
			createContext = true;
		}
		ContextInjectionFactory.invoke(handler, SetEnabled.class, executionContext, staticContext,
				Boolean.TRUE);
		if (createContext) {
			staticContext.dispose();
