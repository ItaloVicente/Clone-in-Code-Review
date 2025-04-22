	@Override
	@PersistState
	void persistState() {
		super.persistState();

		if (!Workbench.getInstance().isClosing()) {
			reference.persist();
		}
	}

	public static void clearOpaqueMenuItems(MenuManagerRenderer renderer, MMenu menu) {
