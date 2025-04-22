		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				ObservableValueStub observable = new ObservableValueStub();
				try {
					observable.setValue(null);
				} catch (UnsupportedOperationException e) {
				}
