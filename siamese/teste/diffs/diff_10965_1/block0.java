		// Initialize BindingPersistence, its needed to install
		// a preferences change listener. See bug 266604.
		bp = new BindingPersistence(manager, commandManager) {
			@Override
			public void reRead() {
				super.reRead();
				// after having read the registry and preferences, persist
				// and update the model
				persistToModel(manager.getActiveScheme());
			}
		};
