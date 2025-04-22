		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.toArray();
			}
		}, (CurrentRealm) collection.getRealm());
