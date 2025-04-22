	private IEclipseContext createContext(final Event trigger) {
		final IEclipseContext staticContext = EclipseContextFactory.create("keys-staticContext"); //$NON-NLS-1$
		staticContext.set(Event.class, trigger);
		return staticContext;
	}

