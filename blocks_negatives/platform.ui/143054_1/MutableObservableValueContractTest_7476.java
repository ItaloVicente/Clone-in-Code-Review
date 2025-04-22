		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				observable.setValue(delegate.createValue(observable));
			}
		}, (CurrentRealm) observable.getRealm());
