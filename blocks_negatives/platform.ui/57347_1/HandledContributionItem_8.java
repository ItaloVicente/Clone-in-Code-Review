	private IStateListener stateListener = new IStateListener() {
		@Override
		public void handleStateChange(State state, Object oldValue) {
			updateState();
		}
	};
