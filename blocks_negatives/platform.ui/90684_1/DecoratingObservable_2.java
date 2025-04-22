			staleListener = new IStaleListener() {
				@Override
				public void handleStale(StaleEvent staleEvent) {
					DecoratingObservable.this.handleStaleEvent(staleEvent);
				}
			};
