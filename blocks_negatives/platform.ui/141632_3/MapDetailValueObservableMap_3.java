	private IStaleListener detailStaleListener = new IStaleListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleStale(StaleEvent staleEvent) {
			addStaleDetailObservable((IObservableValue<E>) staleEvent.getObservable());
		}
