	private IStaleListener staleListener = new IStaleListener() {
		@Override
		public void handleStale(StaleEvent staleEvent) {
			fireStale();
		}
	};
