	private IStaleListener detailStaleListener = new IStaleListener() {
		@Override
		public void handleStale(StaleEvent staleEvent) {
			boolean wasStale = isStale();
			staleDetailObservables.add((staleEvent.getObservable()));
			if (!wasStale) {
				fireStale();
			}
