			staleListener = new IStaleListener() {
				@Override
				public void handleStale(StaleEvent staleEvent) {
					getRealm().exec(new Runnable() {
						@Override
						public void run() {
							makeStale();
						}
					});
				}
			};
