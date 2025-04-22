		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.iterator();
			}
		}, (CurrentRealm) collection.getRealm());
