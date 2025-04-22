		IObservable[] result = ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.runAndIgnore(new Runnable() {
					@Override
					public void run() {
						new ObservableStub();
					}
				});
			}
		});
