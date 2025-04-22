		Realm.runWithDefault(newRealm, new Runnable() {
			@Override
			public void run() {
				assertEquals("new realm should be default", newRealm, Realm.getDefault());
			}
		});
