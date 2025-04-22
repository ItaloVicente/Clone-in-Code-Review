		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				observable.getValue();
			}
		}, (CurrentRealm) observable.getRealm());
