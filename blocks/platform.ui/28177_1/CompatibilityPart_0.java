	@PersistState
	protected void persistState() {
		ContextInjectionFactory.invoke(wrapped, PersistState.class, part.getContext(), null);
	}

	@Persist
	protected void persist() {
		ContextInjectionFactory.invoke(wrapped, Persist.class, part.getContext(), null);
	}

