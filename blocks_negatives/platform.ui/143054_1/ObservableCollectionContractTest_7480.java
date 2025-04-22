		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.size();
			}
		}, (CurrentRealm) collection.getRealm());
