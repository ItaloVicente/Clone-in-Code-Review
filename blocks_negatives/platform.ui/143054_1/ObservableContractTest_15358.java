		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				observable.isStale();
			}
		}, (CurrentRealm) observable.getRealm());
