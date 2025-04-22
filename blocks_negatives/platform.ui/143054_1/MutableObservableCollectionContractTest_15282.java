		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.retainAll(Collections.EMPTY_LIST);
			}
		}, (CurrentRealm) collection.getRealm());
