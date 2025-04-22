	private IStaleListener masterStaleListener = new IStaleListener() {
		@Override
		public void handleStale(StaleEvent staleEvent) {
			fireStale();
		}
	};
