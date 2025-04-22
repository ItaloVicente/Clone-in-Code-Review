		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.clear();
			}
		}, (CurrentRealm) collection.getRealm());
