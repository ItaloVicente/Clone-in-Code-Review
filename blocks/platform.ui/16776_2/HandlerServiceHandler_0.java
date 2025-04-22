		IEclipseContext staticContext = getStaticContext(executionContext);
		IEclipseContext localStaticContext = null;
		try {
			if (staticContext == null) {
				staticContext = localStaticContext = EclipseContextFactory
						.create(HandlerServiceImpl.TMP_STATIC_CONTEXT);
				staticContext.set(HandlerServiceImpl.PARM_MAP, event.getParameters());
			}
			return ContextInjectionFactory.invoke(handler, Execute.class, executionContext,
					staticContext, null);
		} finally {
			if (localStaticContext != null) {
				localStaticContext.dispose();
			}
		}
