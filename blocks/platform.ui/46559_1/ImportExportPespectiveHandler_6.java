	private void logError(PreferenceChangeEvent event, Exception e) {
		logger.error(e, String.format("Cannot read perspective \"%s\" from preferences", event.getKey())); //$NON-NLS-1$
	}

	public PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(PerspectiveReader.class, perspReader);
		return ContextInjectionFactory.make(PerspectiveBuilder.class, childContext);
	}

