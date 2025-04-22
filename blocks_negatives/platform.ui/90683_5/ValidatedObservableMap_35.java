	private IStaleListener targetStaleListener = new IStaleListener() {
		@Override
		public void handleStale(StaleEvent staleEvent) {
			fireStale();
		}
	};
