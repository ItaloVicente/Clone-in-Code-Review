	private IStaleListener detailStaleListener = new IStaleListener() {
		@Override
		public void handleStale(StaleEvent staleEvent) {
			addStaleDetailObservable((IObservableValue<?>) staleEvent
					.getObservable());
		}
	};
