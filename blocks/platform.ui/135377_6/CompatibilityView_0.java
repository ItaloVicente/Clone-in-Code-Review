	@Override
	@PersistState
	void persistState() {
		super.persistState();

		if (!Workbench.getInstance().isClosing()) {
			reference.persist();
		}
	}

