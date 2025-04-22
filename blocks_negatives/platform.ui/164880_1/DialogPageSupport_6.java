		aggregateStatusProvider.addStaleListener(new IStaleListener() {
			@Override
			public void handleStale(StaleEvent staleEvent) {
				currentStatusStale = true;
				handleStatusChanged();
			}
