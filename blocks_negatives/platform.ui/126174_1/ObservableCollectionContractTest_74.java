		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.isEmpty();
			}
		}, (CurrentRealm) collection.getRealm());
