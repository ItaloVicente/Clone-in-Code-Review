		IStateListener listener = new IStateListener() {

			@Override
			public void handleStateChange(State state, Object oldValue) {
				updateToggle(state, valueToUpdate);
			}
		};
		updateToggle(togglestate, valueToUpdate);
		togglestate.addListener(listener);
		stateListeners.put(togglestate, listener);
	}

	private void updateToggle(State state, AtomicBoolean valueToUpdate) {
