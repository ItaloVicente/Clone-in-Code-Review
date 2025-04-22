		IObservable[] collected = ObservableTracker
				.runAndCollect(new Runnable() {
					@Override
					public void run() {
						created[0] = new ObservableStub();
					}
				});
