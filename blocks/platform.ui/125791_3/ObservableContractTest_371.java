		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				observable.isStale();
			}
		}, "isStale", observable);
